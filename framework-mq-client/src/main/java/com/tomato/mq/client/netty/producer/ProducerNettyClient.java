package com.tomato.mq.client.netty.producer;

import com.tomato.mq.client.support.SystemConfig;
import com.tomato.mq.support.constant.Constant;
import com.tomato.mq.support.netty.handler.SignalDecoder;
import com.tomato.mq.support.netty.handler.SignalEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.ConnectException;
import java.nio.charset.Charset;

/**
 * @author Hunhun
 *         2015-10-20 17:12
 */
public class ProducerNettyClient {

//    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerNettyClient.class);
    private static final int PORT =  Integer.parseInt(SystemConfig.PROPERTIES.get(SystemConfig.MQ_SERVER_PORT));
    private static final String HOST = SystemConfig.PROPERTIES.get(SystemConfig.MQ_SERVER_HOST);
    private Channel channel;
    private EventLoopGroup eventLoopGroup;
    private Bootstrap bootstrap;

    public Channel getSocketChannel() throws InterruptedException, ConnectException {
        return connect();
    }

    private Channel connect() throws InterruptedException, ConnectException {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(getChannelInitializer());
        ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
        if(future.isSuccess()){
            channel = future.channel();
        }
        return channel;
    }

    private ChannelInitializer<SocketChannel> getChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                pipeline.addLast(new StringDecoder(Charset.forName(Constant.DEFAULT_ENCODING)));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast(new StringEncoder(Charset.forName(Constant.DEFAULT_ENCODING)));
                pipeline.addLast(new SignalEncoder());
                pipeline.addLast(new SignalDecoder());
                pipeline.addLast(new ProducerHandler());
            }
        };
    }

    public void releaseConnections(){
        if(eventLoopGroup != null){
            eventLoopGroup.shutdownGracefully();
        }
    }

}
