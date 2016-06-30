package com.tomato.mq.support.constant;

/**
 * 系统常量
 * Created by Administrator on 2015/7/16.
 */
public class Constant {

    public static final String DEFAULT_ENCODING = "utf-8";

    /**
     * 系统日志队列名称
     */
    public static final String DEST_SYS_LOG = "sys-log";

    /**
     * 业务日志队列名称
     */
    public static final String DEST_BIZ_LOG = "biz-log";

    /**
     * 系统事件队列名称
     */
    public static final String DEST_SYS_EVENT = "sys-event";

    /**
     * 业务消息队列名称
     */
    public static final String DEST_BIZ_MESSAGE = "biz-message";

    /**
     * 单个Mina端口可持有session的初始容量
     */
    public static final Integer IO_SESSION_LIMIT = 10;

    /**
     * 持久订阅用户是否处于可用状态
     * ACTIVE:可用
     */
    public static final String ACTIVE = "ACTIVE";

    /**
     * 持久订阅用户是否处于可用状态
     * IN_ACTIVE:不可用
     */
    public static final String IN_ACTIVE = "IN_ACTIVE";

    /**
     * 持久订阅用户REDIS-KEY前缀
     */
    public static final String DURABLE_CONSUMER = "DURABLE_CONSUMER:";

    /**
     * 持久订阅用户未消费信息REDIS-KEY前缀
     */
    public static final String DURABLE_CONTENT = "DURABLE_CONTENT:";
}
