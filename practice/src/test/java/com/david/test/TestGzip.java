package com.david.test;

/**
 * Created by jiakang on 2017/8/3.
 */
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;

public class TestGzip {

    private final static String url = "http://www.baidu.com";

    public static void main(String[] args) throws Exception{
        HttpClient http = new HttpClient();
        CustomGetMethod get = new CustomGetMethod(url);

        //添加头信息告诉服务端可以对Response进行GZip压缩
        get.setRequestHeader("Accept-Encoding", "gzip, deflate");
        try {
            int statusCode = http.executeMethod(get);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + get.getStatusLine());
            }

            //打印解压后的返回信息
            System.out.println(get.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("页面无法访问");
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
    }
}
