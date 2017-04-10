package com.david.executor;

import java.util.concurrent.*;

/**
 * Created by haojk on 4/7/17.
 */
public class FutureTaskTest {

    private final ConcurrentMap<Object, Future<String>> taskCache = new ConcurrentHashMap<>();

    private String executionTask(final String taskName) {
        while (true) {
            Future<String> future = taskCache.get(taskName);  //1.1, 2.1
            if (future == null) {
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return taskName;
                    }
                };

                //1.2创建任务
                FutureTask<String> futureTask = new FutureTask<>(task);
                future = taskCache.putIfAbsent(taskName, futureTask);  //1.3
                if (future == null) {
                    future = futureTask;
                    futureTask.run();  //1.4执行任务
                }
            }

            try {
                return future.get();  //1.5, 2.2线程在此等待任务执行完成
            } catch (CancellationException e) {
                e.printStackTrace();
                taskCache.remove(taskName, future);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
