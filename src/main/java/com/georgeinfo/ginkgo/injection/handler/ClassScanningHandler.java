package com.georgeinfo.ginkgo.injection.handler;

import com.georgeinfo.ginkgo.dynamic.ScannerException;
import com.georgeinfo.ginkgo.injection.context.impl.DefaultApplicationContextImpl;
import com.georgeinfo.ginkgo.injection.exception.DIException;

import java.util.Set;

/**
 * 类扫描处理器接口
 * @author George (GeorgeWorld@qq.com)
 */
public interface ClassScanningHandler {
    public boolean scanningAndProcessing(DefaultApplicationContextImpl defaultApplicationContext, Set<String> classpathFileSet) throws ScannerException, DIException;
}
