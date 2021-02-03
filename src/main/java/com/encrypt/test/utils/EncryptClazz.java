package com.encrypt.test.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chen
 * @create 2021-02-01 16:30
 */
public class EncryptClazz {
    /**
     * 加密组主函数
     *
     * @param args ｛"com.encrypt.test.clazz.TestEncrypt","com.encrypt.test.clazz.TestEncryptClazz"｝
     */
    public static void main(String[] args) {
        if (null != args && args.length > 0) {
            for (String arg : args) {
                List<String> list = Arrays.asList(arg.split("\\."));
                String homePath = EncryptClazz.class.getResource("/").getPath();
                String clazzPath = list.stream().collect(Collectors.joining(File.separator));
                // s : "/D:/chen/space/encrypt_class_test/target/classes/com\encrypt\test\clazz\TestEncrypt.class"
                // log.warn("encrypt ----- " + homePath + clazzPath);
                System.err.println("encrypt ----- " + homePath + clazzPath);
                encryptClass(homePath, clazzPath);
            }
        }
    }

    /**
     * 执行 class 加密
     *
     * @param clazzPath 被加密对象 "com\encrypt\test\clazz\Test.class"
     */
    public static void encryptClass(String homePath, String clazzPath) {
        // 被加密文件
        clazzPath += ".class";
        File classFile = new File(homePath + clazzPath);
        if (!classFile.exists()) {
            return;
        }
//        // 加密后文件 // 放到 web 目录下
//        List<String> paths = Arrays.asList(homePath.split("/"));
//        paths.set(paths.size() - 3, "bdsc-web");
//        homePath = paths.stream().collect(Collectors.joining(File.separator));
        File newClassFile = new File(homePath + File.separator + clazzPath + "es");
        try {
            newClassFile.getParentFile().mkdirs();
            newClassFile.createNewFile();
        } catch (IOException ie) {
            //log.error(ie.getMessage(), ie);
            ie.printStackTrace();
            return;
        }
        try (
                FileInputStream fileInputStream = new FileInputStream(classFile);
                BufferedInputStream bis = new BufferedInputStream(fileInputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(newClassFile);
                BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)
        ) {
            int data;
            while ((data = bis.read()) != -1) {
                bos.write(data ^ 0xFF);
            }
            bos.flush();
        } catch (IOException e) {
            //log.error(e.getMessage(), e);
            e.printStackTrace();
            return;
        }
        // 删除源文件
        classFile.delete();
        // 被加密文件重命名
        // newClassFile.renameTo(new File(clazzPath));
    }

}
