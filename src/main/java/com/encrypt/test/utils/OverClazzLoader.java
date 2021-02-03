package com.encrypt.test.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chen
 * @create 2021-02-02 10:20
 */
@Component
public class OverClazzLoader extends ClassLoader {

    /**
     * 调用指定方法
     * todo 如果使用需重写方法
     *
     * @param url
     * @param method
     * @param args
     * @return
     */
    public static Object callMethod(String url, String method, Object... args) {
        try {
            Class myClass = new OverClazzLoader().findClass(url);
            Object aClass = myClass.newInstance();
            if (Objects.isNull(args)) {
                Method methods = myClass.getDeclaredMethod(method);
                return methods.invoke(aClass);
            } else {
                Class<?>[] clazzArr = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    clazzArr[i] = args[i].getClass();
                }
                Method methods = myClass.getDeclaredMethod(method, clazzArr);
                return methods.invoke(aClass, args);
            }
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finds the class with the specified <a href="#name">binary name</a>.
     * This method should be overridden by class loader implementations that
     * follow the delegation model for loading classes, and will be invoked by
     * the {@link #loadClass <tt>loadClass</tt>} method after checking the
     * parent class loader for the requested class.  The default implementation
     * throws a <tt>ClassNotFoundException</tt>.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class could not be found
     * @since 1.2
     */
    @Override
    public Class<?> findClass(String name) {
        Class<?> aClass;
        try {
            aClass = super.findSystemClass(name);
            if (Objects.nonNull(aClass)) {
                return aClass;
            }
            return null;
        } catch (Exception ea) {
            List<String> list = Arrays.asList(name.split("\\."));
            String homePath = EncryptClazz.class.getResource("/").getPath();
            String clazzPath = list.stream().collect(Collectors.joining(File.separator));
            byte[] data = DecryptClazz.decryptClazz(homePath, clazzPath);
            int length = data == null ? 0 : data.length;
            return defineClass(name, data, 0, length);
        }
    }


    /**
     * 通过反射调用方法
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.err.println(callMethod("com.encrypt.test.clazz.TestService", "test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
