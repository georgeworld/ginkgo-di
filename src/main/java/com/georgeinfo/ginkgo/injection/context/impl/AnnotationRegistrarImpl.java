package com.georgeinfo.ginkgo.injection.context.impl;

import com.georgeinfo.ginkgo.dynamic.ScannerException;
import com.georgeinfo.ginkgo.injection.exception.DIException;
import com.georgeinfo.ginkgo.injection.context.ContextProvider;
import com.georgeinfo.ginkgo.injection.context.BaseApplicationContext;
import com.georgeinfo.ginkgo.injection.context.BeanRegister;

/**
 * 扫描注解完成Bean注册
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class AnnotationRegistrarImpl implements BeanRegister {

    private final BaseApplicationContext context;

    public AnnotationRegistrarImpl() {
        context = BaseApplicationContext.getInstance();
    }

    public ContextProvider setBeanPackPath(String[] packPath) throws ScannerException, DIException {
        if (packPath != null && packPath.length > 0) {
            //统一格式，兼容使用者“com.georgeinfo/controller”这种不标准输入
            for (int i = 0; i < packPath.length; i++) {
                packPath[i] = packPath[i].replace("/", ".");
            }

            //让上下文容器去扫描指定包路径下的所有注解标示bean
            context.scanAndregisterBean(packPath);
        }

        return new ContextProvider(context);
    }

}
