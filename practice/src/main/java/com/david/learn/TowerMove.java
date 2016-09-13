package com.david.learn;

/**
 * Created by fsdevops on 9/13/16.
 */
public class TowerMove {

    private static int count = 0;

    public static void main(String[] args) {
        int nDisks = 64;
        long start = System.currentTimeMillis();
        doTowers(nDisks, 'A', 'B', 'C');
        long end = System.currentTimeMillis();
        double runTime = (double)(end - start) / 1000L;
        System.out.println("共经过了" + count + "次移动, 耗时" + runTime + "s");
    }

    public static void doTowers(int topN, char from,
                                char inter, char to) {
        if (topN == 1) {
            System.out.println("Disk 1 from "
                    + from + " to " + to);
            count++;
        } else {
            doTowers(topN - 1, from, to, inter);
            System.out.println("Disk "
                    + topN + " from " + from + " to " + to);
            count++;
            doTowers(topN - 1, inter, from, to);
        }
    }
}
