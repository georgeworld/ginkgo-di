package com.georgeinfo.ginkgo.injection;

import com.georgeinfo.ginkgo.injection.context.impl.DefaultApplicationContextImpl;
import com.georgeinfo.ginkgo.injection.exception.DIException;

/**
 * 依赖注入应用入口类
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class DI {
    /**
     * 根据接口类（或者Bean类本身）得到其实现类对象
     *
     * @param interfaceClass 所要查询的bean实现类所对应的接口类（或者bean实现类本身）
     * @return 查询出的bean实现类对象
     */
    public static <T> T getBeanByInterface(Class<T> interfaceClass) throws DIException {
        T obj = DefaultApplicationContextImpl.getInstance().getBeanInstanceByInterfaceName(interfaceClass.getName());
        if (obj != null) {
            return obj;
        }

        return null;
    }

    /**
     * 根据bean ID查询bean实例
     *
     * @param beanId bean注册时所提供的ID，一般是短类名（短接口名）的驼峰字符串
     * @return 查询出的bean实现类对象
     **/
    public static <T> T getBeanById(String beanId) throws DIException {
        return (T) DefaultApplicationContextImpl.getInstance().getBeanInstanceById(beanId);
    }
}
