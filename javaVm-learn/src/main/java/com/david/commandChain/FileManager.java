package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class FileManager {

    //ls命令
    public static String ls(String path) {
        return "file\nfile2\nfile3\nfile4";
    }

    //ls -l命令
    public static String ls_l(String path) {
        String str = "drw-rw-rw root system 1024 2017-1-25 10:47 file1\n";
        str += "drw-rw-rw root system 1024 2017-1-25 10:47 file2\n";
        str += "drw-rw-rw root system 1024 2017-1-25 10:47 file3";
        return str;
    }

    //ls -a命令
    public static String ls_a(String path) {
        String str = ".\n..\nfile1\nfile2\nfile3";
        return str;
    }
}
