package com.david.learn.threadPool;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by jiakang on 2017/12/25.
 */
@Data
@AllArgsConstructor
public class MyTask implements Runnable {
    private int id;
    private String name;

    @Override
    public void run() {
        try {
            System.out.println("run taskId = " + this.id);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
