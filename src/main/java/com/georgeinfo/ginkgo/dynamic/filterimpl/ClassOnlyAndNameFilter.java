package com.georgeinfo.ginkgo.dynamic.filterimpl;

import com.georgeinfo.ginkgo.dynamic.FileNameFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * 仅受理.class文件，并且对类名字后缀（如UserController.class这个类文件，<br>
 * Controller就是它的类名字后缀）有要求的过滤器
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ClassOnlyAndNameFilter implements FileNameFilter {
    private String classNameSuffix;

    public ClassOnlyAndNameFilter(String classNameSuffix) {
        this.classNameSuffix = classNameSuffix;
    }

    @Override
    public boolean accept(String fileName) {
        if (fileName.endsWith(".class")) {
            String withoutClassNameSuffix = StringUtils.removeEnd(fileName, ".class");
            return withoutClassNameSuffix.endsWith(classNameSuffix);
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
