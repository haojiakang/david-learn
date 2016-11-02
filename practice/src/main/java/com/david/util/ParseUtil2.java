package com.david.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016/11/2.
 */
public class ParseUtil2 {

    private static final String SEPARATOR = " ";

    public static String combineMultiSpell(List<String[]> multiSpell) {
        List<String[]> result = convertMultiSpell(multiSpell);
        List<String> resultList = new ArrayList<>();
        for (String[] split : result) {
            addDistict(resultList, combineSpell(split));
        }
        return concactList(resultList);
    }

    private static String concactList(List<String> resultList) {
        StringBuilder sb = new StringBuilder();
        for (String str : resultList) {
            sb.append(str);
            sb.append(SEPARATOR);
        }
        return sb.toString();
    }

    private static List<String[]> convertMultiSpell(List<String[]> multiSpell) {
        List<String[]> result = new ArrayList<>();
        if (multiSpell.size() == 1) {
            String[] duoyin = multiSpell.get(0);
            for (String yin : duoyin) {
                result.add(new String[]{yin});
            }
        } else {
            List<String[]> subList = multiSpell.subList(1, multiSpell.size());
            List<String[]> subResult = convertMultiSpell(subList);
            String[] duoyin = multiSpell.get(0);
            for (String yin : duoyin) {
                for (String[] subSplit : subResult) {
                    String[] newSplit = new String[subSplit.length + 1];
                    newSplit[0] = yin;
                    System.arraycopy(subSplit, 0, newSplit, 1, subSplit.length);
                    result.add(newSplit);
                }
            }
        }
        return result;
    }

    public static List<String> combineSpell(String... spells) {
        String[][] results = parseSpell(spells);
        List<String> tempList = concact(results[0]);
        if (results[1] != null) {
            List<String> tempList1 = concact(results[1]);
            addDistict(tempList, tempList1);
        }
        return tempList;
    }

    private static String[][] parseSpell(String... spells) {
        String[][] results = new String[2][];
        String firstStr = spells[0];
        String firstChar = firstChar(firstStr);
        if (spells.length == 1) {
            results[0] = new String[2];
            results[0][0] = firstStr;
            results[0][1] = firstChar;
            return results;
        }
        String[] subSpells = new String[spells.length - 1];
        System.arraycopy(spells, 1, subSpells, 0, subSpells.length);
        String[][] subResult = parseSpell(subSpells);
        String[] oldArray = subResult[0];
        int oldArrayLength = oldArray.length;
        results[0] = new String[2 * oldArrayLength];
        for (int i = 0; i < oldArrayLength; i++) {
            String oldStr = oldArray[i];
            results[0][i] = firstStr + oldStr;
            results[0][oldArrayLength + i] = firstChar + oldStr;
        }
        int result1Length = oldArrayLength;
        String[] oldArray1 = subResult[1];
        if (oldArray1 != null) {
            result1Length += oldArray1.length;
        }
        results[1] = new String[result1Length];
        System.arraycopy(oldArray, 0, results[1], 0, oldArrayLength);
        if (oldArray1 != null) {
            System.arraycopy(oldArray1, 0, results[1], oldArrayLength, oldArray1.length);
        }
        return results;
    }

    private static List<String> concact(String[] result) {
        List<String> resultList = new ArrayList<>();
        addDistict(resultList, result);
        return resultList;
    }

    private static void addDistict(List<String> target, String[] source) {
        for (String str : source) {
            if (!target.contains(str)) {
                target.add(str);
            }
        }
    }

    private static void addDistict(List<String> target, List<String> source) {
        for (String str : source) {
            if (!target.contains(str)) {
                target.add(str);
            }
        }
    }

    private static String firstChar(String str) {
        return str.substring(0, 1);
    }
}
