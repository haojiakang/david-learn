package com.david.learn;

import java.util.concurrent.*;

/**
 * Created by jiakang on 2017/12/23.
 */
public class ThreadPoolTest {

    private static double[] d;

    private static class ThreadPoolExecutorTask implements Callable<Integer> {
        private int first;
        private int last;

        public ThreadPoolExecutorTask(int first, int last) {
            this.first = first;
            this.last = last;
        }

        public Integer call() {
            int subCount = 0;
            for (int i = first; i <= last; i++) {
                if (d[i] < 0.5) {
                    subCount++;
                }
            }
            return subCount;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        d = createArrayOfRandomDoubles();
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 4, Long.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue());
        Future<Integer>[] f = new Future[4];
        int size = d.length / 4;
        for (int i = 0; i < 3; i++) {
            f[i] = tpe.submit(new ThreadPoolExecutorTask(i * size, (i + 1) * size - 1));
        }
        f[3] = tpe.submit(new ThreadPoolExecutorTask(3 * size, d.length - 1));
        int n = 0;
        for (int i = 0; i < 4; i++) {
            n += f[i].get();
        }
        System.out.println("Found " + n + " values");
    }

    private static double[] createArrayOfRandomDoubles() {
        return new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2};
    }
}
