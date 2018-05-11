package com.georgeinfo.ginkgo.dynamic;

import java.io.File;

/**
 * 扫描包路径时，过滤文件名的定义接口
 *
 * @author George (GeorgeWorld@qq.com)
 */
public interface FileNameFilter {
    /**
     * 判断一个文件，是否应该被识别处理，并添加到文件名字列表中
     *
     * @param fileName 待被检测的文件名（.class文件，或者classpath中的 其他文件，如config.xml文件等），不包括目录名
     * @return 是否应该被添加到文件列表中
     */
    public boolean accept(String fileName);

    /**
     * 对文件名进行加工处理
     *
     * @param fileName    待被处理的原始文件名，如：UserController.class，不包括目录路径
     * @param packagePath 包路径，如：com.georgeinfo.controller，可以为null或空
     * @return 处理后的文件名
     */
    public String getFileName(String packagePath, String fileName);
}
