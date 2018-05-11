package com.georgeinfo.ginkgo.dynamic.filterimpl;

import com.georgeinfo.ginkgo.dynamic.FileNameFilter;
import org.apache.commons.lang.StringUtils;

/**
 * 只受理.class文件的过滤器
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ClassOnlyFilter implements FileNameFilter {
    @Override
    public boolean accept(String fileName) {
        if (fileName.endsWith(".class")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 组装扫描到的类名字
     *
     * @param packagePath 包路径，如：com.georgeinfo.controller
     * @return 类全路径，如：com.georgeinfo.controller.User
     **/
    @Override
    public String getFileName(String packagePath, String fileName) {
        String className = StringUtils.removeEnd(fileName, ".class");
        if (packagePath != null && !packagePath.trim().isEmpty()) {
            return packagePath + "." + className;
        } else {
            return className;
        }
    }
}
