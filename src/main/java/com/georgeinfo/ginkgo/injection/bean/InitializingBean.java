package com.georgeinfo.ginkgo.injection.bean;

/**
 * Bean初始化器接口
 *
 * @author George (GeorgeWorld@qq.com)
 */
public interface InitializingBean {
    public void afterPropertiesSet();
}