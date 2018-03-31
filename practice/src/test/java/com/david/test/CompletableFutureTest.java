package com.david.test;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by jiakang on 2017/12/25.
 */
public class CompletableFutureTest {

    @Test
    public void testThenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " word").join();
        System.out.println(result);
    }

    @Test
    public void testThenApplyFan() {
        String result = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hello";
            }
        }).thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s + " word";
            }
        }).join();
        System.out.println(result);
    }

    @Test
    public void test1() {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahaha");
            }
        }).join();
    }

    @Test
    public void test2() {
        System.out.println(
                CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        return "test";
                    }
                }).join()
        );
    }

    @Test
    public void testThenAccept() {
        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hello";
            }
        }).thenAcceptAsync(s -> System.out.println(s + " world"));
    }

    @Test
    public void testThenRun() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
            return "hello";
        }).thenRun(() -> {
            System.out.println(Thread.currentThread());
            System.out.println("hello world");
        });
        System.out.println(Thread.currentThread());
        while (true) {
        }
    }

    @Test
    public void testThreadGroup() {
        ThreadGroup threadGroup = new ThreadGroup("mainGroup");
        Thread thread1 = new Thread(threadGroup, "thread1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
            }
        };
        Thread thread2 = new Thread(threadGroup, "thread2") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
            }
        };
        thread1.start();
        thread2.start();
        System.out.println(Thread.currentThread());
        while (true) {
        }
    }

    @Test
    public void testThenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }

    @Test
    public void testThenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));
        while (true) {
        }
    }

    @Test
    public void testRunAfterBoth() {
        Stopwatch stopWatch = Stopwatch.createStarted();
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println(Thread.currentThread() + ", " + stopWatch + ", hello world"));
        System.out.println(Thread.currentThread());
        System.out.println(stopWatch);
        while (true) {
        }
    }

    @Test
    public void testApplyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), s -> s).join();
        System.out.println(result);
    }

    @Test
    public void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println(Thread.currentThread() + ", hello world"));
        while (true) {
        }
    }

    @Test
    public void testExceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (true) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    @Test
    public void testWhenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println(s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    @Test
    public void testHandle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }

    @Test
    public void testThenCompose() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "s2")).join();
        System.out.println(result);
    }
}
