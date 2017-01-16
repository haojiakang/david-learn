package com.david.util;

import java.util.Calendar;

/**
 * Created by haojk on 12/5/16.
 */
public class WebChatUtil {


    public static boolean webchatEnable(String language){
        return true;
    }

    public static String getWebChatPages(String language) {
        return "webChatPage";
    }

    private static boolean webchatInHours(){
        return false;
    }

    private static boolean webchatLanguageEnable(String language){
        return true;
    }

    private static Calendar getCurrentTime(){
        return Calendar.getInstance();
    }
}
