package com.georgeinfo.ginkgo.injection.handler;

import com.georgeinfo.ginkgo.dynamic.ScannerException;
import com.georgeinfo.ginkgo.injection.annotation.Service;
import com.georgeinfo.ginkgo.injection.context.impl.DefaultApplicationContextImpl;
import com.georgeinfo.ginkgo.injection.exception.DIException;
import com.georgeinfo.ginkgo.injection.util.DIBasicUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author George (GeorgeWorld@qq.com)
 */
public class ClassScanningHandlerDefaultImpl implements ClassScanningHandler {
    @Override
    public boolean scanningAndProcessing(DefaultApplicationContextImpl context, Set<String> classpathFileSet) throws ScannerException, DIException {
        //处理扫描到的注解标注类（默认只处理@Service一种注解）
        if (classpathFileSet == null || classpathFileSet.isEmpty()) {
            return false;
        }

        //循环处理所有扫描得到的类
        for (String fileClasspath : classpathFileSet) {
            //尝试实例化扫描得到的类的对象
            Class<?> clazz = null;
            fileClasspath = StringUtils.removeEnd(fileClasspath.replace("/", "."), ".class");
            try {
                clazz = Class.forName(fileClasspath);
            } catch (ClassNotFoundException ex) {
                throw new ScannerException("### Class[" + fileClasspath + "] Not Found when default handler processing.", ex);
            }

            //判断扫描到的类，是否标注了@Service注解
            if (clazz.isAnnotationPresent(Service.class)) {
                Service serviceAnnotation = clazz.getAnnotation(Service.class);
                String beanId = serviceAnnotation.name();
                if (beanId == null || beanId.trim().isEmpty()) {
                    //将类短名字转换为驼峰字符串
                    beanId = DIBasicUtil.classNameToBeanName(clazz);
                }

                context.addBean(beanId, clazz, serviceAnnotation.beanSope());
            }
        }

        return true;

    }
}
