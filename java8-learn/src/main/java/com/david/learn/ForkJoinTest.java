package com.david.learn;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by jiakang on 2017/12/23.
 */
public class ForkJoinTest {
    private static double[] d;

    private static class ForkJoinTask extends RecursiveTask<Integer> {
        private int first;
        private int last;

        public ForkJoinTask(int first, int last) {
            this.first = first;
            this.last = last;
        }

        protected Integer compute() {
            int subCount;
            if (last - first < 10) {
                subCount = 0;
                for (int i = first; i <= last; i++) {
                    if (d[i] < 0.5)
                        subCount++;
                }
            } else {
                int mid = (first + last) >>> 1;
                ForkJoinTask left = new ForkJoinTask(first, mid);
                left.fork();
                ForkJoinTask right = new ForkJoinTask(mid + 1, last);
                right.fork();
                subCount = left.join();
                subCount += right.join();
            }
            return subCount;
        }
    }

    public static void main(String[] args) {
        d = createArrayOfRandomDoubles();
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int n = forkJoinPool.invoke(new ForkJoinTask(0, d.length - 1));
        System.out.println("Found " + n + " values");
    }

    private static double[] createArrayOfRandomDoubles() {
        return new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2};
    }
}
