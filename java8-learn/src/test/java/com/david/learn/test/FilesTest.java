package com.david.learn.test;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by jiakang on 2017/6/12.
 */
public class FilesTest {

    @Test
    public void testFiles() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("pom.xml"));
        lines.forEach(System.out::println);
    }
}
