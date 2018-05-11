package com.georgeinfo.ginkgo.injection.util;

/**
 * 基础工具类
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class DIBasicUtil {
    /**
     * 将类短名字转换为驼峰字符串
     *
     * @param clazz 待被提取短名字的类
     * @return 转换后的bean名字
     */
    public static String classNameToBeanName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        //将类短名字转换为驼峰字符串
        String simpleName = clazz.getSimpleName();
        String firstChar = simpleName.substring(0, 1).toLowerCase();
        String beanName = firstChar + simpleName.substring(1, simpleName.length());
        return beanName;
    }
}
