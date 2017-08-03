package com.david.test.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * Created by jiakang on 2017/7/17.
 */
public class PerfMonAgent {
    private static Instrumentation inst = null;

    public static void premain(String agentArgs, Instrumentation _inst) {
        System.out.println("PerfMonAgent.premain() was called.");
        // Initiate the static variables we use to track information
        inst = _inst;
        // Set up the class-file transformer.
        ClassFileTransformer trans = new PerfMonXformer();
        System.out.println("Adding a PerfMonXformer instance to the JVM");
        inst.addTransformer(trans);
    }
}
