package com.david.learn;

import com.david.common.Common;

/**
 * Created by Jackie on 2016/7/23.
 */
public class ReferenceCountingGC {

    public Object instance = null;


    /**
     * 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清楚是否被回收过
     */
    private byte[] bigSize = new byte[2 * Common._1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        //假设在这行发生GC,objA和objB能否被回收？
        System.gc();
    }

}
