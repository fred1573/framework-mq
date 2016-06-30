package com.tomasky.framework.mc.netty.consumer;

import com.tomasky.framework.mc.netty.handler.HeartbeatHandler;
import com.tomato.mq.support.constant.Constant;
import com.tomato.mq.support.netty.handler.SignalDecoder;
import com.tomato.mq.support.netty.handler.SignalEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author Hunhun
 *         2015-10-19 17:31
 */
@Component
public class MsgServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MsgServerHandler msgServerHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 60, 0));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast(new StringDecoder(Charset.forName(Constant.DEFAULT_ENCODING)));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringEncoder(Charset.forName(Constant.DEFAULT_ENCODING)));
        pipeline.addLast(new SignalEncoder());
        pipeline.addLast(new HeartbeatHandler());
        pipeline.addLast(new SignalDecoder());
        pipeline.addLast(msgServerHandler);

    }
}
