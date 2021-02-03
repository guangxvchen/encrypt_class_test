package com.encrypt.test.utils;

import java.io.*;

/**
 * @author chen
 * @create 2021-02-02 10:14
 */
public class DecryptClazz {


    public static void writeFile(String path, byte[] data) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        bos.write(data);
        bos.flush();
    }

    /**
     * 执行解密
     *
     * @param homePath
     * @param clazzPath
     * @return
     */
    public static byte[] decryptClazz(String homePath, String clazzPath) {
        // 从 web 目录下调用
//        List<String> paths = Arrays.asList(homePath.split("/"));
//        paths.set(paths.size() - 3, "bdsc-web");
//        homePath = paths.stream().collect(Collectors.joining(File.separator));
        File encryptedClazz = new File(homePath + File.separator + clazzPath + ".classes");
        if (!encryptedClazz.exists()) {
            System.err.println("decryptClass File:" + clazzPath + ".classes not found!");
            return null;
        }
        byte[] result = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(encryptedClazz));
            bos = new ByteArrayOutputStream();
            int data;
            while ((data = bis.read()) != -1) {
                bos.write(data ^ 0xFF);
            }
            bos.flush();
            result = bos.toByteArray();
        } catch (Exception e) {
            // log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                // log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return result;
    }

}
