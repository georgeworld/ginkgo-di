package com.georgeinfo.ginkgo.injection.config;

import com.georgeinfo.ginkgo.injection.handler.ClassScanningHandler;

/**
 * 基础配置类
 * @author George (GeorgeWorld@qq.com)
 */
public interface DIConfiguration {
    public ClassScanningHandler getClassScanningHandler();
}
