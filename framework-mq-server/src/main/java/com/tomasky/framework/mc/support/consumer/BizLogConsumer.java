package com.tomasky.framework.mc.support.consumer;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author frd
 *         2016/6/27.
 */
public class BizLogConsumer extends AbstractConsumer{

    public BizLogConsumer(String consumerId, ChannelHandlerContext ctx) {
        super(consumerId, ctx);
    }

}
