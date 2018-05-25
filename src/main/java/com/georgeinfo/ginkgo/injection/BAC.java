package com.georgeinfo.ginkgo.injection;

import com.georgeinfo.ginkgo.injection.context.ApplicationContext;
import com.georgeinfo.ginkgo.injection.context.impl.AnnotationRegistrarImpl;
import com.georgeinfo.ginkgo.injection.context.impl.ManualRegisterImpl;

/**
 * DI上下文环境初始化器（DI初始化入口类）<br>
 * BAC = Base Application Context
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class BAC {
    /**
     * 扫描指定包路径下的注解标注类，完成初始化
     */
    public static AnnotationRegistrarImpl annotation() {
        return new AnnotationRegistrarImpl();
    }
    /**
     * 扫描指定包路径下的注解标注类，完成初始化
     * 使用给定的context容器作为初始化根基
     */
    public static AnnotationRegistrarImpl annotation(ApplicationContext context) {
        return new AnnotationRegistrarImpl(context);
    }

    /**
     * 手动注册类
     */
    public static ManualRegisterImpl manual() {
        return new ManualRegisterImpl();
    }
    /**
     * 手动注册类
     * 使用给定的context容器作为初始化根基
     */
    public static ManualRegisterImpl manual(ApplicationContext context) {
        return new ManualRegisterImpl(context);
    }
}
