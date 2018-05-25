package com.georgeinfo.ginkgo.injection.context.impl;

import com.georgeinfo.ginkgo.dynamic.ClasspathFileScanner;
import com.georgeinfo.ginkgo.dynamic.ScannerException;
import com.georgeinfo.ginkgo.injection.context.ApplicationContext;
import com.georgeinfo.ginkgo.injection.exception.DIException;
import com.georgeinfo.ginkgo.injection.util.DIBasicUtil;
import com.georgeinfo.ginkgo.injection.bean.BeanScope;
import com.georgeinfo.ginkgo.injection.bean.BeanWrapper;
import com.georgeinfo.ginkgo.injection.annotation.Service;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 基础应用上下文容器（Bean对象容器）
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class DefaultApplicationContextImpl implements ApplicationContext {
    /**
     * bean实例容器map，key=Bean ID，一般是类短名（第一个字母小写）
     */
    private final ConcurrentHashMap<String, BeanWrapper> beanMap = new ConcurrentHashMap<String, BeanWrapper>();
    /**
     * bean接口名字与bean id 映射关系容器，key=bean的接口名字（全名），和bean类自己的全名，value=bean id
     */
    private final ConcurrentHashMap<String, String> interfaceNameBeanIdMap = new ConcurrentHashMap<String, String>();

    /**
     * 私有构造方法，确保本类的对象是一个单例
     */
    private DefaultApplicationContextImpl() {
    }

    //## 线程安全的惰性单例模式 开始 #################################
    private static class ConfigurationHolder {
        private static final DefaultApplicationContextImpl instance = new DefaultApplicationContextImpl();
    }

    public static DefaultApplicationContextImpl getInstance() {
        return ConfigurationHolder.instance;
    }
    //## 线程安全的惰性单例模式 结束 #################################

    /**
     * 向上下文环境添加一个bean
     */
    public boolean addBean(String beanId, Class<?> beanImpl, BeanScope beanScope) throws DIException {
        BeanWrapper bean = new BeanWrapper(beanScope, beanImpl);
        //向以beanId为key的容器注册bean
        beanMap.put(beanId, bean);

        //向以接口类名或者实现类类名为key的容器注册bean
        Class<?>[] interfaces = beanImpl.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> iface : interfaces) {
                interfaceNameBeanIdMap.put(iface.getName(), beanId);
            }
        }

        //以bean自己的类名称为key，向容器注册bean
        String beanClassName = beanImpl.getName();
        interfaceNameBeanIdMap.put(beanClassName, beanId);

        return true;
    }

    /**
     * 啥都不干，以后有用
     */
    public boolean buildContext() {
        return true;
    }

    /**
     * 扫描一个指定包路径，并把扫描到的类实例化后，加入上下文容器中
     *
     * @param packPath 待被扫描的包路径，如：com.georgeinfo.service
     */
    public void scanAndregisterBean(String[] packPath) throws ScannerException, DIException {
        //通用类扫描器，扫描时只收集类，不做具体处理，具体处理时再遍历一遍扫描到的类集合，工作职责分割清楚
        Set<String> classpathFileSet = ClasspathFileScanner.getFileNameOfPackage(packPath);
        //遍历扫描到的类，实例化并加入上下文容器中
        for (String fileClasspath : classpathFileSet) {
            Class<?> clazz = null;
            fileClasspath = StringUtils.removeEnd(fileClasspath.replace("/", "."), ".class");
            try {
                clazz = Class.forName(fileClasspath);
            } catch (ClassNotFoundException ex) {
                throw new ScannerException("### Class[" + fileClasspath + "] Not Found.", ex);
            }

            //处理扫描到的注解标注类（目前只处理@Service一种注解，以后可以增加，或则会把处理注解的过程外放延后到应用层）
            //判断扫描到的类，是否标注了@Service注解
            if (clazz.isAnnotationPresent(Service.class)) {
                Service serviceAnnotation = clazz.getAnnotation(Service.class);
                String beanId = serviceAnnotation.name();
                if (beanId == null || beanId.trim().isEmpty()) {
                    //将类短名字转换为驼峰字符串
                    beanId = DIBasicUtil.classNameToBeanName(clazz);
                }

                addBean(beanId, clazz, serviceAnnotation.beanSope());
            }
        }

    }


    public ConcurrentHashMap<String, BeanWrapper> getInstanceMap() {
        return this.beanMap;
    }

    public void registerClassNameBeanIdMapping(String interfaceName, String beanId) {
        interfaceNameBeanIdMap.put(interfaceName, beanId);
    }

    public <T> T getBeanInstanceByInterfaceName(String interfaceName) throws DIException {
        String beanId = interfaceNameBeanIdMap.get(interfaceName);
        if (beanId != null) {
            return (T) beanMap.get(beanId.trim()).getInstance();
        }
        return null;
    }

    public <T> T getBeanInstanceById(String beanId) throws DIException {
        return (T) beanMap.get(beanId.trim()).getInstance();
    }

    public ConcurrentHashMap<String, String> getInterfaceNameBeanIdMap() {
        return this.interfaceNameBeanIdMap;
    }

    /**
     * 查询当前DI容器中，一共有多少个bean实例
     */
    public int getBeanSize() {
        return beanMap.size();
    }
}
