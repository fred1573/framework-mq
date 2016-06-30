package com.tomasky.framework.mc.netty.consumer;

import com.tomasky.framework.mc.netty.client.ConsumerReversePool;
import com.tomasky.framework.mc.netty.model.ConnectionSuccess;
import com.tomasky.framework.mc.netty.model.MsgSend;
import com.tomasky.framework.mc.support.event.model.ConnectionSucEvent;
import com.tomasky.framework.mc.support.event.model.MsgSendEvent;
import com.tomasky.framework.mc.support.event.publisher.IMessageEventPublisher;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author Administrator
 *         2015-10-19 17:34
 */
@Component
@ChannelHandler.Sharable
public class MsgServerHandler extends SimpleChannelInboundHandler<Signal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgServerHandler.class);

    @Autowired
    private IMessageEventPublisher eventPublisher;
    @Autowired
    private ConsumerReversePool consumerReversePool;

    @Override
    @Transactional
    protected void channelRead0(ChannelHandlerContext ctx, Signal msg) throws Exception {
        Signal.RequestType rType = msg.getReqType();
        switch (rType) {
            case BEAT:
                break;
            case TRANSPORT:
                eventPublisher.publishEvent(new MsgSendEvent(new MsgSend(msg)));
                break;
            case ACK_SUCCESS:
                eventPublisher.publishEvent(new ConnectionSucEvent(new ConnectionSuccess(ctx, msg)));
                break;
            case ACK_FAIL:
                break;
            default:
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            LOGGER.debug("监听到IOException");
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("进入到channelInactive方法");
        consumerReversePool.remove(ctx);
    }
}
