package com.david.test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by jiakang on 2017/12/24.
 */
public class UrlEncoderTest {


    public static void main(String[] args) {
        String str = "我是从α星系来到地球打酱油的";
        str = "胸有成竹";
        try {
            //对汉字进行编码
            String enStr = URLEncoder.encode(str, "utf-8");
            //输出结果：%E6%88%91%E6%98%AF%E4%BB%8E%CE%B1%E6%98%9F%E7%B3%BB%E6%9D%A5%E5%88%B0%E5%9C%B0%E7%90%83%E6%89%93%E9%85%B1%E6%B2%B9%E7%9A%84
            System.out.println(enStr);
            System.out.println("*******************");
            //对编码后的字符串进行解码
            String deStr = URLDecoder.decode(enStr, "utf-8");
            //输出结果：我是从α星系来到地球打酱油的
            System.out.println(deStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch b00lock
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws UnsupportedEncodingException {
        String str = "%E8%83%B8%E6%9C%89%E6%88%90%E7%AB%B9";
        System.out.println(URLDecoder.decode(str, "utf-8"));
    }
}
