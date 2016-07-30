package com.david.learn;

import com.david.common.Common;

/**
 * Created by David on 2016/7/30.
 * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 */
public class PretenureSizeThresholdTest {

    public static void main(String[] args) {
        byte[] allocation;
        allocation = new byte[4 * Common._1MB];
    }

}
