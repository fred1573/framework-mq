package com.tomato.mq.client.support;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hunhun on 2015/11/27.
 */
public class ThreadPoolExecutorBuilder {


    private static ThreadPoolExecutor threadPoolExecutor = null;

    private ThreadPoolExecutorBuilder() {
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(ThreadPoolExecutorModel threadPoolExecutorModel) {
        if (threadPoolExecutor == null) {
            synchronized (ThreadPoolExecutorBuilder.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(threadPoolExecutorModel.getCorePoolSize(), threadPoolExecutorModel.getMaxPoolSize(), threadPoolExecutorModel.getKeepAliveTime(), TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(threadPoolExecutorModel.getBlockingCapacity()));
                }
            }
        }
        return threadPoolExecutor;
    }

}
