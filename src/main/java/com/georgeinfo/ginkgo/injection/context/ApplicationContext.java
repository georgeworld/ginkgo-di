package com.georgeinfo.ginkgo.injection.context;

import com.georgeinfo.ginkgo.dynamic.ScannerException;
import com.georgeinfo.ginkgo.injection.bean.BeanScope;
import com.georgeinfo.ginkgo.injection.bean.BeanWrapper;
import com.georgeinfo.ginkgo.injection.exception.DIException;

import java.util.concurrent.ConcurrentHashMap;


/**
 * 基础应用上下文容器（Bean对象容器）
 *
 * @author George (GeorgeWorld@qq.com)
 */
public interface ApplicationContext {


    /**
     * 向上下文环境添加一个bean
     */
    public boolean addBean(String beanId, Class<?> beanImpl, BeanScope beanScope) throws DIException;

    /**
     * 啥都不干，以后有用
     */
    public boolean buildContext();

    /**
     * 扫描一个指定包路径，并把扫描到的类实例化后，加入上下文容器中
     *
     * @param packPath 待被扫描的包路径，如：com.georgeinfo.service
     */
    public void scanAndregisterBean(String[] packPath) throws ScannerException, DIException;


    public ConcurrentHashMap<String, BeanWrapper> getInstanceMap();

    public void registerClassNameBeanIdMapping(String interfaceName, String beanId);

    public <T> T getBeanInstanceByInterfaceName(String interfaceName) throws DIException;

    public <T> T getBeanInstanceById(String beanId) throws DIException;

    public ConcurrentHashMap<String, String> getInterfaceNameBeanIdMap();

    /**
     * 查询当前DI容器中，一共有多少个bean实例
     */
    public int getBeanSize();
}
