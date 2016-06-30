package com.tomasky.framework.mc.support.consumer;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author frd
 *         2016/6/28.
 */
public class SysLogConsumer extends AbstractConsumer {

    public SysLogConsumer(String consumerId, ChannelHandlerContext ctx) {
        super(consumerId, ctx);
    }
}
