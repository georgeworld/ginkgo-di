package com.georgeinfo.ginkgo.injection.annotation;

import com.georgeinfo.ginkgo.injection.bean.BeanScope;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Service实例注解
 *
 * @author George (GeorgeWorld@qq.com)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    public String name() default "";

    public BeanScope beanSope() default BeanScope.singleton;
}
