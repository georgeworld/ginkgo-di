package com.georgeinfo.ginkgo.dynamic;

import com.georgeinfo.ginkgo.dynamic.filterimpl.ClassOnlyAndNameFilter;
import com.georgeinfo.ginkgo.dynamic.filterimpl.ClassOnlyFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * 类扫描器
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ClasspathFileScanner {

    /**
     * 查询一个指定包路径下面所有的.class类文件（对类名字没有特殊要求），递归扫描子包
     *
     * @param pack 指定被扫描的包路径，如：com.georgeinfo.controller
     * @return 扫描到的类名字集合，如：[com.georgeinfo.controller.User,com.georgeinfo.controller.Customer]等
     */
    public static Set<String> getFileNameOfPackage(String[] pack) throws ScannerException {
        return scanFilesInClassPath(pack, true, new ClassOnlyFilter());
    }

    /**
     * 扫描指定的包路径，把扫描到的类的类名（包括包路径的类全名）汇聚成一个集合，返回，对类名字没有特定要求。
     *
     * @param pack      待被扫描的包路径，如：com.georgeinfo.controller
     * @param recursive 是否递归扫描子包
     * @return 扫描到的类名字集合，如：[com.georgeinfo.controller.User,com.georgeinfo.controller.Customer]等
     **/
    public static Set<String> getClassNameOfPackage(String[] pack, boolean recursive) throws ScannerException {
        return scanFilesInClassPath(pack, recursive, new ClassOnlyFilter());
    }

    /**
     * 扫描指定的包路径，把扫描到的类的类名（包括包路径的类全名）汇聚成一个集合，返回，对类名字有特定后缀要求。
     *
     * @param pack            待被扫描的包路径，如：com.georgeinfo.controller
     * @param classNameSuffix 如果指定了此参数，符合指定字符串结尾的类名字，才被识别，<br>
     *                        比如指定为“Controller”，则com.georgeinfo.UserController这样的类才会被识别，com.georgeinfo.User这样的类会被忽略。
     * @param recursive       是否递归扫描子包
     * @return 扫描到的类名字集合，如：[com.georgeinfo.controller.User,com.georgeinfo.controller.Customer]等
     **/
    public static Set<String> getClassNameOfPackage(String[] pack, String classNameSuffix, boolean recursive) throws ScannerException {
        if (classNameSuffix != null && !classNameSuffix.trim().isEmpty()) {
            return scanFilesInClassPath(pack, recursive, new ClassOnlyAndNameFilter(classNameSuffix));
        } else {
            return scanFilesInClassPath(pack, recursive, new ClassOnlyFilter());
        }
    }

    /**
     * 扫描指定的包路径，把扫描到的类的类名（包括包路径的类全名）汇聚成一个集合，返回
     *
     * @param packs          待被扫描的包路径，如：com.georgeinfo.controller，如果要全classpath都扫描，就传入new String[]{"/"}
     * @param fileNameFilter 文件名称过滤器
     * @param recursive      是否递归扫描子包
     * @return 扫描到的类文件路径集合，具体类文件路径的格式，由参数@link{FileNameFilter}中的getFileName(...)来决定
     **/
    public static Set<String> scanFilesInClassPath(String[] packs, boolean recursive, FileNameFilter fileNameFilter) throws ScannerException {
        if (packs == null || packs.length == 0) {
            return null;
        }

        Set<String> filesInClassPath = new LinkedHashSet();
        for (String pack : packs) {
            String packageName = pack;
            if(packageName.trim().equals("/")){
                packageName = ""; //从根目录开始扫描所有类
            }
            String packageDirName = packageName.replace('.', '/');

            try {
                Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
                //遍历指定包路径下的所有文件
                while (dirs.hasMoreElements()) {
                    URL url = (URL) dirs.nextElement();
                    String protocol = url.getProtocol();

                    //扫到的直接是.class文件或者classpath文件夹
                    if ("file".equals(protocol)) {
//                    logger.info("## Classpath file scanning in classpath.");

                        //文件的物理路径
                        String filePhysicalPath = URLDecoder.decode(url.getFile(), "UTF-8");

                        //将扫描到的class文件，加入到fileClassPaths中
                        findAndAddFile(packageName, filePhysicalPath, recursive, filesInClassPath, fileNameFilter);
                    } else if ("jar".equals(protocol)) {//如果扫描到的是.jar包
//                    logger.info("## Classpath file scanning in jar.");
                        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();

                        //再去读取jar包里的class文件
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = (JarEntry) entries.nextElement();
                            String name = entry.getName();

                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }

                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');

                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }

                                if ((idx != -1) || (recursive)) {
                                    if (!entry.isDirectory()) {//如果不是目录，才受理
                                        if (fileNameFilter.accept(name)) {//经过了客户过滤器检测，则将该文件处理后加入文件集合中
                                            filesInClassPath.add(fileNameFilter.getFileName(pack, name));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
//            logger.error(ex);
                throw new ScannerException("## Exception when scanning class file.", ex);
            }
        }

        return filesInClassPath;
    }

    /**
     * 添加扫描到的文件，继续扫描发现的子目录
     *
     * @param packageName      当前目录所对应的包路径，如：com.georgeinfo.controller.usercenter
     * @param filePath         当前目录的物理路径
     * @param recursive        是否递归扫描子目录
     * @param filesInClassPath 扫描到的符合条件的文件路径集合
     * @param customFileFilter 调用方自定义的文件名过滤器
     **/
    private static void findAndAddFile(
            String packageName,
            String filePath,
            final boolean recursive,
            Set<String> filesInClassPath,
            final FileNameFilter customFileFilter) {
        File dir = new File(filePath);

        if ((!dir.exists()) || (!dir.isDirectory())) {
            return;
        }

        File[] filesInDir = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (recursive == true && file.isDirectory()) {//如果是文件夹，直接受理
                    return true;
                } else {
                    //如果是文件，则直接调用自定义过滤器
                    return customFileFilter.accept(file.getName());
                }
            }
        });

        //遍历扫描到的文件
        for (File file : filesInDir) {
            if (file.isDirectory()) {//如果是目录，则把包路径累加上当前目录名字，然后则递归继续扫描这个目录
                findAndAddFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        filesInClassPath,
                        customFileFilter);
            } else {//如果扫描到的是最终文件，则尝试放入集合中
                String namePath = customFileFilter.getFileName(packageName, file.getName());

                //将符合条件的文件名字路径，放入集合中
                filesInClassPath.add(namePath);
            }
        }
    }

//    public static void main(String[] args) {
//        Set<String> classSet = getFileNameOfPackage("com.georgeinfo.base.util.dynamic", ".class", true);
//        for (String clazz : classSet) {
//            System.out.println(clazz);
//        }
//    }
}