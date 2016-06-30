package com.tomasky.framework.mc.netty.handler;

import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Administrator
 *         2015-10-20 15:33
 */
@ChannelHandler.Sharable
@Component
public class HeartbeatHandler extends ChannelDuplexHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatHandler.class);

    private int writeBeatFailAmount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (IdleState.WRITER_IDLE.equals(e.state())) {
                Signal heartbeat = new Signal(Signal.RequestType.BEAT);
                ctx.writeAndFlush(heartbeat).addListener(future -> {
                    LOGGER.info(ctx.name() + " 心跳-----");
                    if (!future.isSuccess()) {
                        LOGGER.info(ctx.name() + " 心跳失败次数-----" + writeBeatFailAmount);
                        writeBeatFailAmount++;
                        if (writeBeatFailAmount >= 3) {
                            writeBeatFailAmount = 0;
                            ctx.channel().pipeline().fireExceptionCaught(future.cause());
                        }
                    }
                });
            }
        }else if(evt instanceof ChannelInputShutdownEvent){
            LOGGER.info(ctx.name() + "连接中断");
            ctx.channel().pipeline().fireExceptionCaught(new IOException());
        }
    }

}
