package com.tomasky.framework.mc.support.consumer;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author frd
 *         2016/6/28.
 */
public interface IConsumerHolder {

    /**
     * 注册一个消费者
     * @param consumerId
     * @param ctx
     */
    void register(String consumerId, ChannelHandlerContext ctx);

    /**
     * 注销一个消费者
     * @param consumerId
     * @param ctx
     */
    void unregister(String consumerId, ChannelHandlerContext ctx);

}
