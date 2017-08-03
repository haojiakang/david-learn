package com.david.test;

import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jiakang on 2017/5/9.
 */
public class RemoveDuplicate {

    @Test
    public void doRemove() throws FileNotFoundException {
        String fileName = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/new_flow_black_list_20170502.txt";
        fileName = "/Users/jiakang/oldCode/david-learn/practice/src/test/resources/blacklist_July.July";
        String fileName2 = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/whitelist";
        fileName2 = "/Users/jiakang/oldCode/david-learn/practice/src/test/resources/消息箱内部灰度白名单.txt";
        Set<String> set1 = getSet(fileName);
        Set<String> set2 = getSet(fileName2);
        set1.removeIf(x -> set2.contains(x));
        String newFile = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/newFile.txt";
        newFile = "/Users/jiakang/oldCode/david-learn/practice/src/test/resources/newFile_July.txt";
        writeFile(newFile, set1);
    }

    private Set<String> getSet(String fileName) {
        try (FileReader reader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            Set<String> set = new HashSet<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                set.add(line.trim());
            }
            return set;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeFile(String fileName, Set<String> set) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));) {
            for (String line : set) {
                if(line != null && line.length() > 0) {
                    bufferedWriter.write(line.trim());
                    bufferedWriter.write("\r\n");
                }
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
