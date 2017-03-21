package com.david.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by haojk on 3/1/17.
 */
public class Deprecated {

    public static void main(String[] args) throws InterruptedException {
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);
        //将PrintThread进行暂停,输出内容工作停止
        printThread.suspend();
        System.out.println("main suspend PrintThread at " + DateUtils.getFormatDate());
        TimeUnit.SECONDS.sleep(3);
        //将PrintThread进行恢复,输出内容继续
        printThread.resume();
        System.out.println("main resume PrintThread at " + DateUtils.getFormatDate());
        TimeUnit.SECONDS.sleep(3);
        //将PrintThread进行终止,输出内容停止
        printThread.stop();
        System.out.println("main stop PrintThread at " + DateUtils.getFormatDate());
        TimeUnit.SECONDS.sleep(3);
    }

    static class Runner implements Runnable {

        @Override
        public void run() {

            while (true) {
                System.out.println(Thread.currentThread().getName() + " Run at " + DateUtils.getFormatDate());
                SleepUtils.second(1);
            }
        }
    }
}
