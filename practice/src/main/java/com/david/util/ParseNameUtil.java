package com.david.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by David on 2016/11/2.
 */
public class ParseNameUtil {

    /**
     * 组合各种可能的拼音
     * 参数例如hao,jia,kang
     * 返回:
     * haojiakang
     * jiakang
     * kang
     * haojk
     * hjk
     * jk
     *
     * @param spells
     * @return
     */
    public static String combineSpell(String... spells) {
        List<String> fulls = new ArrayList<>();
        List<String> firsts = new ArrayList<>();
        List<String> combines = new ArrayList<>();
        for (int i = spells.length - 1; i >= 0; i--) {
            String spell = spells[i];
            String first = firstChar(spell);
            String combine = spell;
            if (fulls.size() > 0) {
                spell += fulls.get(fulls.size() - 1);
            }
            if (firsts.size() > 0) {
                first += firsts.get(firsts.size() - 1);
            }
            fulls.add(spell);
            firsts.add(first);
        }
//        firsts.remove(0);
        System.out.println(Arrays.asList(spells));
        System.out.println(fulls);
        System.out.println(firsts);
        return fulls.toString();
    }

    public static String firstChar(String name) {
        if (isNullOrBlank(name)) {
            return "";
        }
        return name.substring(0, 1);
    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.length() == 0;
    }

    public static void main(String[] args) {
        String str = combineSpell("hao", "jia", "kang");
        String str2 = combineSpell("xia", "hou", "jin", "xuan");
//        System.out.println(str);
    }
}
