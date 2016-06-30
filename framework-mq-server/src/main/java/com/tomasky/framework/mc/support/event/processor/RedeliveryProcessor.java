package com.tomasky.framework.mc.support.event.processor;

import com.tomato.mq.support.message.MessageType;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Hunhun
 *         2015-09-10-18:03
 */
public interface RedeliveryProcessor {

    /**
     * 根据消息类型及用户标识重发消息
     * @param messageType
     * @param consumerId
     * @param ctx
     */
    void redeliveryMsg(MessageType messageType, String consumerId, ChannelHandlerContext ctx);
}
