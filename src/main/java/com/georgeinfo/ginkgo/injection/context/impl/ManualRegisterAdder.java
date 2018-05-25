package com.georgeinfo.ginkgo.injection.context.impl;

import com.georgeinfo.ginkgo.injection.context.ApplicationContext;
import com.georgeinfo.ginkgo.injection.exception.DIException;
import com.georgeinfo.ginkgo.injection.util.DIBasicUtil;
import com.georgeinfo.ginkgo.injection.bean.BeanScope;
import com.georgeinfo.ginkgo.injection.context.ManualBeanRegister;

/**
 * 手动注册bean的添加器
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ManualRegisterAdder implements ManualBeanRegister {
    private ApplicationContext context;

    public ManualRegisterAdder(ApplicationContext context) {
        this.context = context;
    }

    public ManualRegisterAdder add(String beanId, Class<?> beanImpl, BeanScope beanScope) throws DIException {
        if (beanImpl == null) {
            return this;
        }

        context.addBean(beanId, beanImpl, beanScope);

        return this;
    }

    public ManualRegisterAdder add(Class<?> beanImpl, BeanScope beanScope) throws DIException {
        //将类短名字转换为驼峰字符串
        String beanId = DIBasicUtil.classNameToBeanName(beanImpl);

        //注册bean
        return add(beanId, beanImpl, beanScope);
    }

    public ManualRegisterAdder add(Class<?> beanImpl) throws DIException {
        return add(beanImpl, BeanScope.singleton);
    }

    public ApplicationContext build() {
        context.buildContext();

        return context;
    }
}
