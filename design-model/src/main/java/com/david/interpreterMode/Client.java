package com.david.interpreterMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haojk on 1/15/17.
 */
public class Client {

    //运算四则运算
    public static void main(String[] args) throws IOException {
        String expStr = getExpStr();
        //赋值
        Map<String, Integer> var = getValues(expStr);
        Calculator calculator = new Calculator(expStr);
        System.out.println("运算结果为: " + expStr + "=" + calculator.run(var));
    }

    //获得表达式
    private static String getExpStr() throws IOException {
        System.out.println("请输入表达式:");
        return (new BufferedReader(new InputStreamReader(System.in)).readLine());
    }

    //获得映射值
    public static Map<String, Integer> getValues(String expStr) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        //解析有几个参数要传递
        for (char ch : expStr.toCharArray()) {
            if (ch != '+' && ch != '-') {
                //解决重复参数的问题
                if (!map.containsKey(String.valueOf(ch))) {
                    String in = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    map.put(String.valueOf(ch), Integer.valueOf(in));
                }
            }
        }
        return map;
    }
}
