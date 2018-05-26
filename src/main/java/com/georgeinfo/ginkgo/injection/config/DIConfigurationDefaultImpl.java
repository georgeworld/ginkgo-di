package com.georgeinfo.ginkgo.injection.config;

import com.georgeinfo.ginkgo.injection.handler.ClassScanningHandler;
import com.georgeinfo.ginkgo.injection.handler.ClassScanningHandlerDefaultImpl;

/**
 * DI配置默认实现类
 * @author George (GeorgeWorld@qq.com)
 */
public class DIConfigurationDefaultImpl implements DIConfiguration {
    @Override
    public ClassScanningHandler getClassScanningHandler() {
        return new ClassScanningHandlerDefaultImpl();
    }
}
