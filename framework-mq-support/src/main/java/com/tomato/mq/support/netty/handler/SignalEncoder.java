package com.tomato.mq.support.netty.handler;

import com.alibaba.fastjson.JSON;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Administrator
 *         2015-10-17 11:33
 */
public class SignalEncoder extends MessageToMessageEncoder<Signal> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Signal msg, List<Object> out) throws Exception {
        String jsonString = JSON.toJSONString(msg);
        out.add(jsonString);
    }
}
