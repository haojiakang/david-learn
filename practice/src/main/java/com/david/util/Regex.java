package com.david.util;

import com.google.common.base.Strings;

/**
 * Created by haojk on 11/9/16.
 */
public class Regex {

    public static String parseRegex(String str) {
        if (Strings.isNullOrEmpty(str) || !str.contains("@")) {
            return "";
        }
        int beginIndex = str.indexOf("@") + 1;
        int endIndex = str.indexOf(" ");
        if (endIndex != -1) {
            return str.substring(beginIndex, endIndex);
        }
        return str.substring(beginIndex);
    }

    public static String parseRegex1(String str) {
        String rawRegex = "@.*";
        if (str.matches(rawRegex)) {
            String regex = "@\\S+ \\S*";
            if (str.matches(regex)) {
                return str.substring(1, str.indexOf(" "));
            }
            return str.substring(1);
        }
        return "";
    }

    public static void main(String[] args) {
        String str = "@五红米-华为 simulate";
//        str = "@五红米-华为 ";
        str = "@hongmi_123";
        str = "@";
        System.out.println(parseRegex1(str));
    }
}
