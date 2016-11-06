package com.david.cisco;

/**
 * Created by haojk on 11/4/16.
 */
public class RouterUtil {

    public static String calculateRouter(int num) {
        if (num < 0) {
            return "Invalid number!";
        }
        int count = num / 5;
        int result = num * 10 - count * 5;
        return "RouterInterfaces(" + num + ")->" + result;
    }

    public static String calculateRouter2(int num) {
        int result = 0;
        for (int i = 1; i <= num; i++) {
            if (i % 5 == 0) {
                result += 5; //result = result + 5;
            } else {
                result += 10;
            }
        }
        return "RouterInterfaces(" + num + ")->" + result;
    }

    public static String calculateRouter1(int num) {
        return "RouterInterfaces(" + num + ")->" + calculateRouterInt(num);
    }

    private static int calculateRouterInt(int num) {
        if (num <= 0) {
            return 0;
        }
        if (num == 1) {
            return interfaceNum(num);
        } else {
            return calculateRouterInt(num - 1) + interfaceNum(num);
        }
    }

    private static int interfaceNum(int num) {
        if (num < 0) {
            return 0;
        }
        return num % 5 == 0 ? 5 : 10;
    }
}
