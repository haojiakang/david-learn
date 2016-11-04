package com.david.cisco;

/**
 * Created by haojk on 11/4/16.
 */
public class RouterUtil {

    public static String calculateRouter(int num) {
        if (num == 0) {
            return "Invalid number!";
        }
        int count = num / 5;
        int result = num * 10 - count * 5;
        return "RouterInterfaces("+num+")->"+result;
    }
}
