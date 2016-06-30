package com.tomato.mq.support.message;

/**
 * @author Hunhun
 *         14:26
 */
public enum MessageType {

    /**
     * 系统日志
     */
    SYS_LOG,

    /**
     * 业务日志
     */
    BIZ_LOG,

    /**
     * 业务消息
     */
    BIZ_MESSAGE,

    /**
     * 系统消息，系统之间的事件
     */
    SYS_EVENT
}
