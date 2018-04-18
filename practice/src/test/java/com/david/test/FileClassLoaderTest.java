package com.david.test;

import com.david.common.util.Consoles;
import com.david.learn.classLoader.DemoObj;
import com.david.learn.classLoader.FileClassLoader;
import com.david.learn.classLoader.FileUrlClassLoader;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by jiakang on 2018/4/17
 *
 * @author jiakang
 */
public class FileClassLoaderTest {

    @Test
    public void testLoadFileClass() {
        String rootDir = "/Users/jiakang/";
        FileClassLoader loader = new FileClassLoader(rootDir);

        try {
            //加载指定的class文件
            Class<?> object1 = loader.loadClass("com.david.learn.classLoader.DemoObj");
            Consoles.info(object1.newInstance().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadDiff() {
        String rootDir = "/Users/jiakang/";
        //创建2个不同的自定义类加载器示例
        FileClassLoader loader1 = new FileClassLoader(rootDir);
        FileClassLoader loader2 = new FileClassLoader(rootDir);
        //通过findClass创建类的Class对象
        try {
            Class<?> object1 = loader1.loadClass("com.david.learn.classLoader.DemoObj");
            Class<?> object2 = loader1.loadClass("com.david.learn.classLoader.DemoObj");

            Consoles.info("findClass->obj1:{}", object1.hashCode());
            Consoles.info("findClass->obj2:{}", object2.hashCode());
            Consoles.info("findClass->systemObj:{}", DemoObj.class.hashCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUrlClassLoader() {
        String rootDir = "/Users/jiakang";
        //创建自定义文件类加载器
        File file = new File(rootDir);
        //File to URI
        URI uri = file.toURI();
        try {
            URL[] urls = {uri.toURL()};

            FileUrlClassLoader loader = new FileUrlClassLoader(urls);

            //加载指定的class文件
            Class<?> object1 = loader.loadClass("com.david.learn.classLoader.DemoObj");
            Consoles.info(object1.newInstance().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHotDeploy() {
        String rootDir = "/Users/jiakang";
        String classPath = "com.david.learn.classLoader.DemoObj";
        //创建自定义文件类加载器
        FileClassLoader loader = new FileClassLoader(rootDir);
        FileClassLoader loader1 = new FileClassLoader(rootDir);

        try {
            //加载指定的class文件,调用loadClass()
            Class<?> object1 = loader.loadClass(classPath);
            Class<?> object2 = loader1.loadClass(classPath);

            System.out.println("loadClass->obj1:" + object1.hashCode());
            System.out.println("loadClass->obj2:" + object2.hashCode());

            //加载指定的class文件,直接调用findClass(),绕过检测机制，创建不同class对象。
            Class<?> object3 = loader.findClass(classPath);
            Class<?> object4 = loader1.findClass(classPath);

            System.out.println("loadClass->obj3:" + object3.hashCode());
            System.out.println("loadClass->obj4:" + object4.hashCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}