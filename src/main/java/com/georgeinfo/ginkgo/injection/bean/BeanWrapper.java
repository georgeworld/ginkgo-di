package com.georgeinfo.ginkgo.injection.bean;

import com.georgeinfo.ginkgo.injection.exception.DIException;

/**
 * Bean包装类
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class BeanWrapper {
    private final BeanScope beanScope;
    private final Class<?> clazz;
    private Object instance;

    public BeanWrapper(BeanScope beanScope, Class<?> clazz) throws DIException {
        this.beanScope = beanScope;
        this.clazz = clazz;

        try {
            if (beanScope.equals(BeanScope.singleton)) {
                this.instance = clazz.newInstance();

                //对于单例注入，bean实例创建完成后，执行bean的初始化方法
                doAfterPropertiesSet(this.instance);
            } else {
                //下边这一句代码的作用是：尝试在最初bean 树初始化时，就检查每个bean类是否有无参构造函数，让可能的异常尽早暴露在系统初始化阶段。
                clazz.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new DIException("## Exception when create bean instance.", ex);
        }
    }

    public BeanWrapper(BeanScope beanScope, Object object) throws DIException {
        this.beanScope = beanScope;
        this.clazz = object.getClass();

        if (beanScope.equals(BeanScope.singleton)) {
            this.instance = object;

            //对于单例注入，bean实例创建完成后，执行bean的初始化方法
            doAfterPropertiesSet(this.instance);
        }
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public Object getInstance() throws DIException {
        if (this.beanScope.equals(BeanScope.prototype)) {
            try {
                Object beanInstance = this.clazz.newInstance();

                doAfterPropertiesSet(beanInstance);
                return beanInstance;
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new DIException("### Exception when create bean instance.", ex);
            }
        } else {
            return this.instance;
        }
    }

    private void doAfterPropertiesSet(Object beanInstance) throws DIException {
        if ((beanInstance instanceof InitializingBean)) {
            InitializingBean ibean = (InitializingBean) beanInstance;
            try {
                ibean.afterPropertiesSet();
            } catch (Exception ex) {
                throw new DIException("## Exception when invoke afterPropertiesSet() method of class[" + this.clazz.getName() + "]", ex);
            }
        }
    }

    public BeanScope getBeanScope() {
        return this.beanScope;
    }
}
