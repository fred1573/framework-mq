package com.tomasky.framework.mc.netty.model;

import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Hunhun
 */
public class ConnectionSuccess {

    private ChannelHandlerContext channelHandlerContext;

    private Signal signal;

    public ConnectionSuccess(ChannelHandlerContext channelHandlerContext, Signal signal) {
        this.channelHandlerContext = channelHandlerContext;
        this.signal = signal;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }
}
