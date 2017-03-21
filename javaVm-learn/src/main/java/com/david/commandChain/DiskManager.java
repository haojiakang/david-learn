package com.david.commandChain;

/**
 * Created by haojk on 1/25/17.
 */
public class DiskManager {

    //默认计算的大小
    public static String df() {
        return "/\t10485760\n/usr\t104857600\n/home\t1048576000\n";
    }

    //按照kb来计算
    public static String df_k() {
        return "/\t10240\n/usr\t102400\n/home\t10240000\n";
    }

    //按照gb来计算
    public static String df_g() {
        return "/\t10\n/usr\t100\n/home\t10000\n";
    }
}
