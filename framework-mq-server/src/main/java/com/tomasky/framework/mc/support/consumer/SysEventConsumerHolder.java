package com.tomasky.framework.mc.support.consumer;

import com.tomato.mq.support.message.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author frd
 *         2016/6/28.
 */
@Component
public class SysEventConsumerHolder implements IConsumerHolder{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysEventConsumerHolder.class);

    private List<SysEventConsumer> sysEventConsumers = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void register(String consumerId, ChannelHandlerContext ctx) {
        boolean exist = false;
        for (SysEventConsumer sysEventConsumer : sysEventConsumers) {
            //同一个消费者，分布式系统中多个结点注册同一个消费者
            if (consumerId.equals(sysEventConsumer.getConsumerId())) {
                exist = true;
                sysEventConsumer.register(ctx);
                break;
            }
        }
        if (!exist) {
            sysEventConsumers.add(new SysEventConsumer(consumerId, ctx));
        }
        LOGGER.info("事件消费者注册成功， consumerId={}, messageType={}", consumerId, MessageType.SYS_EVENT);
    }

    @Override
    public void unregister(String consumerId, ChannelHandlerContext ctx) {
        for (SysEventConsumer sysEventConsumer : sysEventConsumers) {
            if(sysEventConsumer.getConsumerId().equals(consumerId)) {
                List<ChannelHandlerContext> ctxs = sysEventConsumer.getCtxs();
                for (ChannelHandlerContext handlerContext : ctxs) {
                    if(handlerContext.equals(ctx)) {
                        ctxs.remove(ctx);
                        LOGGER.info("事件消费者注销成功， consumerId={}, messageType={}", consumerId, MessageType.SYS_EVENT);
                        return;
                    }
                }
            }
        }
    }

    public List<SysEventConsumer> getSysEventConsumers() {
        return sysEventConsumers;
    }
}
