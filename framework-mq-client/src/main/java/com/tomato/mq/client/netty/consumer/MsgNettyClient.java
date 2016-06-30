package com.tomato.mq.client.netty.consumer;

import com.tomato.mq.client.support.SystemConfig;
import com.tomato.mq.support.constant.Constant;
import com.tomato.mq.support.message.MessageType;
import com.tomato.mq.support.netty.handler.SignalDecoder;
import com.tomato.mq.support.netty.handler.SignalEncoder;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author Administrator
 *         2015-10-20 17:12
 */
public class MsgNettyClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgNettyClient.class);
    private static final int PORT = Integer.parseInt(SystemConfig.PROPERTIES.get(SystemConfig.MQ_SERVER_PORT));
    private static final String HOST = SystemConfig.PROPERTIES.get(SystemConfig.MQ_SERVER_HOST);

    /**
     * 用户标识
     */
    private String consumerId;
    private Set<MessageType> messageTypes;
    private Channel channel;
    private Bootstrap bootstrap;
    private EventLoopGroup eventLoopGroup;
    private SimpleChannelInboundHandler<Signal> channelInboundHandler;

    private static MsgNettyClient instance = null;



    public static MsgNettyClient getInstance(String consumerId, Set<MessageType> messageTypes){
        if(instance == null){
            synchronized (MsgNettyClient.class){
                if(instance == null){
                    instance = new MsgNettyClient(consumerId, messageTypes);
                }
            }
        }
        return instance;
    }


    private MsgNettyClient(String consumerId, Set<MessageType> messageTypes) {
        this.channelInboundHandler = new MsgHandler();
        this.consumerId = consumerId;
        this.messageTypes = messageTypes;
    }

    public Channel getSocketChannel() {
        return connect();
    }

    private Channel connect() {
        if (channel == null || !(channel.isActive())) {
            eventLoopGroup = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .option(ChannelOption.ALLOW_HALF_CLOSURE, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(getChannelInitializer());
            ChannelFuture channelFuture;
            channelFuture = bootstrap.connect(HOST, PORT).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        future.channel().writeAndFlush(new Signal(Signal.RequestType.ACK_FAIL));
                        LOGGER.info(MsgNettyClient.class.getName() + ",port:" + PORT + " 无法连接到服务器，3s后将进行重连");
                        TimeUnit.SECONDS.sleep(3);
                        bootstrap.connect(HOST, PORT).addListener(this);
                    } else {
                        future.channel().writeAndFlush(new Signal(Signal.RequestType.ACK_SUCCESS, consumerId, messageTypes));
                    }
                }
            });
            channel = channelFuture.channel();
        }
        return channel;
    }

    private ChannelInitializer<SocketChannel> getChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
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
                pipeline.addLast(channelInboundHandler);
            }
        };
    }

    @ChannelHandler.Sharable
    private class HeartbeatHandler extends ChannelDuplexHandler {
        private int writeBeatFailAmount = 0;

        @Override
        public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (IdleState.WRITER_IDLE.equals(e.state())) {
                    Signal heartbeat = new Signal(Signal.RequestType.BEAT);
                    ctx.writeAndFlush(heartbeat).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (!future.isSuccess()) {
                                writeBeatFailAmount++;
                                if (writeBeatFailAmount >= 3) {
                                    writeBeatFailAmount = 0;
                                    //心跳无响应， 自动重连
                                    LOGGER.error("------------心跳无响应， 自动重连------------");
                                    future.channel().pipeline().fireExceptionCaught(future.cause());
                                }
                            }
                        }
                    });
                }
            } else if (evt instanceof ChannelInputShutdownEvent) {
                LOGGER.error("............ChannelInputShutdownEvent...........断线重线中...");
                TimeUnit.SECONDS.sleep(3);
                eventLoopGroup.shutdownGracefully();
                channel.close();
                getSocketChannel();
            }
        }

    }

}
