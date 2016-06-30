package com.tomato.mq.support.netty.handler;

import com.alibaba.fastjson.JSON;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Administrator
 *         2015-10-20 15:50
 */
public class SignalDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        Signal signal = JSON.parseObject(msg, Signal.class);
        out.add(signal);
    }
}
