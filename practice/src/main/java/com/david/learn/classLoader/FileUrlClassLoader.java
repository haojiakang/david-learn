package com.david.learn.classLoader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by jiakang on 2018/4/17
 *
 * @author jiakang
 */
public class FileUrlClassLoader extends URLClassLoader {

    public FileUrlClassLoader(URL[] urls) {
        super(urls);
    }
}