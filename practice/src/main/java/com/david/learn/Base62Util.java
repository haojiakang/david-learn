package com.david.learn;


import java.nio.charset.StandardCharsets;

/**
 * Created by jiakang on 2017/7/26.
 */
public class Base62Util {

    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    /**
     * 将data编码成Base62的字符串
     * @param data
     * @return
     */
    public static String encodeBase62(byte[] data) {
        StringBuffer sb = new StringBuffer(data.length * 2);
        int pos = 0, val = 0;
        for (int i = 0; i < data.length; i++) {
            val = (val << 8) | (data[i] & 0xFF);
            pos += 8;
            while (pos > 5) {
                char c = encodes[val >> (pos -= 6)];
                sb.append(
                /**/c == 'i' ? "ia" :
                /**/c == '+' ? "ib" :
                /**/c == '/' ? "ic" : String.valueOf(c));
                val &= ((1 << pos) - 1);
            }
        }
        if (pos > 0) {
            char c = encodes[val << (6 - pos)];
            sb.append(
            /**/c == 'i' ? "ia" :
            /**/c == '+' ? "ib" :
            /**/c == '/' ? "ic" : String.valueOf(c));
        }
        return sb.toString();
    }

    public static String encodeBase62(String str) {
        return encodeBase62(str.getBytes(StandardCharsets.US_ASCII));
    }
}
