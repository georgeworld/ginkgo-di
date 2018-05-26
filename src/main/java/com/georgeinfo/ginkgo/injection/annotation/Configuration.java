package com.georgeinfo.ginkgo.injection.annotation;

import java.lang.annotation.*;

/**
 * DI配置类注解，标注了此注解，表示此类是一个配置实现类
 *
 * @author George (GeorgeWorld@qq.com)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    /**
     * 配置类的名字，其实没什么用途，先占个位置，以后可能有用
     */
    public String name() default "";
}
