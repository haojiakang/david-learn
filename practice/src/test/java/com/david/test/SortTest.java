package com.david.test;

import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortTest {

    @Test
    public void test() {
        List<String> list = Arrays.asList("jack", "fuck", "wtc");
        Collections.sort(list);
        System.out.println(list);
    }
}
