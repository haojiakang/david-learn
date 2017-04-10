package com.david.concurrentContainer;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by haojk on 3/30/17.
 */
public class LoopHashMap {

    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> map = new HashMap<>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "tft");
        t.start();
        t.join();
    }
}
