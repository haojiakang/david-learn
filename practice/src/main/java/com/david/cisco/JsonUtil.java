package com.david.cisco;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by haojk on 11/4/16.
 */
public class JsonUtil {

    public static Map<String, Object> parseJson(String json) {
        Map<String, Object> pairs = new LinkedHashMap<>();
        if (!perhapsJson(json)) {
            return pairs;
        }
        String[] strs = json.split(",");
        for (String str : strs) {
            String[] pair = str.split(":");
            if (pair.length == 2) {
                String roughKey = pair[0];
                String key = parseStr(roughKey);
                String roughValue = pair[1];
                String value = parseStr(roughValue);
                if (key != null && value != null) {
                    if (perhapsJson(value)) {
                        pairs.put(key, parseJson(value));
                    } else {
                        pairs.put(key, value);
                    }
                }
            }
        }
        return pairs;
    }

    private static boolean perhapsJson(String json) {
        if (json == null || json.length() == 0) {
            return false;
        }
        return json.contains("{") && json.contains("}");
    }

    private static String parseStr(String roughtStr) {
        if (roughtStr == null || roughtStr.length() == 0) {
            return null;
        }
        try {
            return roughtStr.substring(roughtStr.indexOf("'") + 1, roughtStr.lastIndexOf("'"));
        } catch (Exception e) {
            return null;
        }
    }
}
