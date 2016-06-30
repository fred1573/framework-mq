package com.tomato.mq.client.support;

/**
 * 线程池初始化数据
 * Created by Hunhun on 2015/11/27.
 */
public class ThreadPoolExecutorModel {

    /**
     * 线程池维护的最小线程数量
     */
    private static final int CORE_POOL_SIZE = 1;

    /**
     * 线程池维护的最大线程数量
     */
    private static final int MAX_POOL_SIZE = 5;

    /**
     * 阻塞队列最大容量
     */
    private static final int BLOCKING_CAPACITY = 1000;

    /**
     * 空闲线程存活最大时间（当线程池中线程数大于CORE_POOL_SIZE时）
     */
    private static final long KEEP_ALIVE_TIME = (long) 300;



    private int corePoolSize;

    private int maxPoolSize;

    private int blockingCapacity;

    private long keepAliveTime;

    public ThreadPoolExecutorModel(int corePoolSize, int maxPoolSize, int blockingCapacity, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.blockingCapacity = blockingCapacity;
        this.keepAliveTime = keepAliveTime;
    }

    public ThreadPoolExecutorModel(int corePoolSize, int maxPoolSize) {
        this(corePoolSize, maxPoolSize, BLOCKING_CAPACITY, KEEP_ALIVE_TIME);
    }

    public ThreadPoolExecutorModel() {
        this(CORE_POOL_SIZE, MAX_POOL_SIZE);
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getBlockingCapacity() {
        return blockingCapacity;
    }

    public void setBlockingCapacity(int blockingCapacity) {
        this.blockingCapacity = blockingCapacity;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}
