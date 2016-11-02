package com.david.test;

import com.david.util.ParseUtil2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016/11/3.
 */
public class ParseUtilTest {

    @Test
    public void testCombineSpell() {
        String name = "xuan";
        String[] name2 = {"jin", "xuan"};
        String[] name3 = {"hou", "jin", "xuan"};
        String[] name4 = {"xia", "hou", "jin", "xuan"};
        List<String> result = ParseUtil2.combineSpell(name);
        List<String> result2 = ParseUtil2.combineSpell(name2);
        List<String> result3 = ParseUtil2.combineSpell(name3);
        List<String> result4 = ParseUtil2.combineSpell(name4);
        System.out.println(result);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }

    @Test
    public void testCombineMultiSpellOne() {
//        List<String[]> multiSpell = new ArrayList<>();
//        multiSpell.add(new String[]{"cha", "chai", "ci"});
//        List<String[]> multiSpell1 = new ArrayList<>();
//        multiSpell1.add(new String[]{"chang", "zhang"});
//        multiSpell1.add(new String[]{"cha", "chai", "ci"});
        List<String[]> multiSpell2 = new ArrayList<>();
        multiSpell2.add(new String[]{"hao"});
        multiSpell2.add(new String[]{"jia"});
        multiSpell2.add(new String[]{"kang"});
//        String result = ParseUtil2.combineMultiSpell(multiSpell);
//        String result1 = ParseUtil2.combineMultiSpell(multiSpell1);
        String result2 = ParseUtil2.combineMultiSpell(multiSpell2);
//        System.out.println(result);
//        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testCombineMultiSpell() {
        List<String[]> multiSpell = new ArrayList<>();
        multiSpell.add(new String[]{"cha", "chai", "ci"});
        List<String[]> multiSpell1 = new ArrayList<>();
        multiSpell1.add(new String[]{"chang", "zhang"});
        multiSpell1.add(new String[]{"cha", "chai", "ci"});
        List<String[]> multiSpell2 = new ArrayList<>();
        multiSpell2.add(new String[]{"dan", "shan", "can"});
        multiSpell2.add(new String[]{"chang", "zhang"});
        multiSpell2.add(new String[]{"cha", "chai", "ci"});
        String result = ParseUtil2.combineMultiSpell(multiSpell);
        String result1 = ParseUtil2.combineMultiSpell(multiSpell1);
        String result2 = ParseUtil2.combineMultiSpell(multiSpell2);
        System.out.println(result);
        System.out.println(result1);
        System.out.println(result2);
    }
}
