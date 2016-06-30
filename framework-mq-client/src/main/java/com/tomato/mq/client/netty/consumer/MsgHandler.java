package com.tomato.mq.client.netty.consumer;

import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Administrator
 *         2015-10-19 18:14
 */
@ChannelHandler.Sharable
public class MsgHandler extends SimpleChannelInboundHandler<Signal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Signal signal) throws Exception {
        Signal.RequestType rType = signal.getReqType();
        switch (rType){
            case TRANSPORT:
                MsgEventPublisher.getInstance().publish(new MsgEvent(signal.getMessage()), new ArrayList<>(signal.getMessageTypes()).get(0));
                break;
            case BEAT:
                LOGGER.info("收到来自服务端的心跳包");
                break;
            case ACK_SUCCESS:
                LOGGER.info("连接成功");
                break;
            case ACK_FAIL:
                LOGGER.error("连接失败" + signal.getMessage());
                ctx.close();
                break;
            default:
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().fireExceptionCaught(new IOException());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof IOException){
            ctx.close();
        }
    }
}
