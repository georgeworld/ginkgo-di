package com.georgeinfo.ginkgo.injection.bean;

/**
 * Bean范围定义类
 *
 * @author George (GeorgeWorld@qq.com)
 */
public enum BeanScope {
    singleton, prototype;

    private BeanScope() {
    }

    /**
     * 根据英文名字，构造出对应的枚举值，如：
     * BeanScope b = build("prototype");
     *
     * @param scopeName 枚举英文名称
     * @return 传入的枚举名称所对应的枚举对象
     **/
    public static BeanScope build(String scopeName) {
        if ((scopeName == null) || (scopeName.trim().isEmpty())) {
            return singleton;
        }
        for (BeanScope v : values()) {
            if (v.name().equals(scopeName)) {
                return v;
            }
        }
        return null;
    }
}