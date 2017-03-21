package com.david.commandChain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by haojk on 1/25/17.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Invoker invoker = new Invoker();
        while (true) {
            //UNIX下的默认提示符号
            System.out.println("#");
            //捕获输出
            String input = (new BufferedReader(new InputStreamReader(System.in))).readLine();
            //输入quit或exit退出
            if ("quit".equals(input) || "exit".equals(input)) {
                return;
            }
            System.out.println(invoker.exec(input));
        }
    }
}
