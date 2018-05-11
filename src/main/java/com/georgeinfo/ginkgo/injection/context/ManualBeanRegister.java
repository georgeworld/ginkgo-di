package com.georgeinfo.ginkgo.injection.context;

import com.georgeinfo.ginkgo.injection.context.impl.ManualRegisterAdder;
import com.georgeinfo.ginkgo.injection.exception.DIException;
import com.georgeinfo.ginkgo.injection.bean.BeanScope;

/**
 * @author George (GeorgeWorld@qq.com)
 */
public interface ManualBeanRegister {
    public ManualRegisterAdder add(String beanId, Class<?> beanImpl, BeanScope beanScope) throws DIException;

    public ManualRegisterAdder add(Class<?> beanImpl, BeanScope beanScope) throws DIException;

    public ManualRegisterAdder add(Class<?> beanImpl) throws DIException;
}
