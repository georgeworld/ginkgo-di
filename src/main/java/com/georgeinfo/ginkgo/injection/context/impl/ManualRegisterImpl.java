package com.georgeinfo.ginkgo.injection.context.impl;

import com.georgeinfo.ginkgo.injection.exception.DIException;
import com.georgeinfo.ginkgo.injection.bean.BeanScope;
import com.georgeinfo.ginkgo.injection.context.ManualBeanRegister;
import com.georgeinfo.ginkgo.injection.context.BaseApplicationContext;
import com.georgeinfo.ginkgo.injection.context.BeanRegister;

/**
 * Bean手动注册器
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ManualRegisterImpl implements BeanRegister,ManualBeanRegister {
    private final BaseApplicationContext context;
    private final ManualRegisterAdder adder;

    public ManualRegisterImpl() {
        context = BaseApplicationContext.getInstance();
        adder = new ManualRegisterAdder(context);
    }


    public ManualRegisterAdder add(String beanId, Class<?> beanImpl, BeanScope beanScope) throws DIException {
        return adder.add(beanId, beanImpl, beanScope);
    }

    public ManualRegisterAdder add(Class<?> beanImpl, BeanScope beanScope) throws DIException {
        return adder.add(beanImpl, beanScope);
    }

    public ManualRegisterAdder add(Class<?> beanImpl) throws DIException {
        return adder.add(beanImpl);
    }
}
