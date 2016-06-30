package com.tomato.mq.client.netty.producer;

import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by frd on 9/9/2015.
 */
public class ProducerHandler extends SimpleChannelInboundHandler<Signal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerHandler.class);
    private static ProducerHandler instance;


    public static ProducerHandler getInstance() {
        if(instance == null){
            synchronized (ProducerHandler.class){
                if(instance == null){
                    instance = new ProducerHandler();
                }
            }
        }
        return instance;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Signal msg) throws Exception {

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
