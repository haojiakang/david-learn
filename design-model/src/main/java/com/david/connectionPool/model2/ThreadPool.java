package com.david.connectionPool.model2;

/**
 * Created by haojk on 3/4/17.
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutdown();

    void addWorkers(int num);

    void removeWorker(int num);

    int getJobSize();
}
