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
        String source = "/Users/jiakang/IdeaProjects/david-learn/practice/src/calculateMinus/resources/new_flow_black_list_20170502.txt";
        source = "/Users/jiakang/IdeaProjects/david-learn/practice/src/calculateMinus/resources/blacklist1705";
        source = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/BlacklistOfJune";
        String toRemove = "/Users/jiakang/IdeaProjects/david-learn/practice/src/calculateMinus/resources/whitelist";
        toRemove = "/Users/jiakang/IdeaProjects/david-learn/practice/src/calculateMinus/resources/ABTest内测白名单文件";
        toRemove = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/ABTest内测白名单文件June";
        Set<String> sourceSet = getSet(source);
        Set<String> toRemoveSet = getSet(toRemove);
        sourceSet.removeIf(x -> toRemoveSet.contains(x));
        String newFile = "/Users/jiakang/IdeaProjects/david-learn/practice/src/calculateMinus/resources/newFile.txt";
        newFile = "/Users/jiakang/IdeaProjects/david-learn/practice/src/calculateMinus/resources/newFile1705.txt";
        newFile = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/newFileJune.txt";
        writeFile(newFile, sourceSet);
    }

    @Test
    public void removeDup() {
        String source = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/intranet_white_list.properties";
        Set<String> sourceSet = getSet(source);
        String newFile = "/Users/jiakang/IdeaProjects/david-learn/practice/src/test/resources/new_intranet_white_list.properties";
        writeFile(newFile, sourceSet);
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
