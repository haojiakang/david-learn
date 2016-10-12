package com.david.lsp;

import com.david.common.Common;

/**
 * Created by David on 2016/7/30.
 * VM args1: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * VM args2: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
 */
public class TenuringThresholdTest {

    public static void main(String[] args) throws InterruptedException {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[Common._1MB / 4];
        //什么时候进入老年代取决于XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * Common._1MB];
        allocation3 = new byte[4 * Common._1MB];
        allocation3 = null;
        allocation3 = new byte[4 * Common._1MB];
        Thread.sleep(1000000000);
    }
}
