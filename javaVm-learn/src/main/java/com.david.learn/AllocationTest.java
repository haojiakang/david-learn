package com.david.learn;

import com.david.common.Common;

/**
 * Created by David on 2016/7/30.
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public class AllocationTest {

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * Common._1MB];
        allocation2 = new byte[2 * Common._1MB];
        allocation3 = new byte[2 * Common._1MB];
        allocation4 = new byte[4 * Common._1MB]; //出现一次MinorGC
    }

}
