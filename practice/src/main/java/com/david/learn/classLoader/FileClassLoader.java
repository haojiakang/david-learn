package com.david.learn.classLoader;

import com.david.common.util.Consoles;

import java.io.*;

/**
 * Created by jiakang on 2018/4/17
 *
 * @author jiakang
 */
public class FileClassLoader extends ClassLoader {

    private String rootDir;

    public FileClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * 编写findClass方法的逻辑
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        //获取类的class文件字节数组
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        //直接生成class对象
        return defineClass(name, classData, 0, classData.length);
    }

    /**
     * 编写获取class文件并转换为字节码流的逻辑
     *
     * @param name
     * @return
     */
    private byte[] getClassData(String name) {
        //读取类文件的字节
        String path = classNameToPath(name);
        try (InputStream ins = new FileInputStream(path);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            //读取类文件的字节码
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 类文件的完全路径
     *
     * @param name
     * @return
     */
    private String classNameToPath(String name) {
        return rootDir + File.separatorChar + name.replace('.', File.separatorChar) + ".class";
    }
}