package com.david.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by haojk on 4/4/17.
 */
public class AtomicIntegerArrayTest {

    static int[] value = new int[]{1, 2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}
