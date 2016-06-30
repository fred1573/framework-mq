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
public class SysLogConsumerHolder implements IConsumerHolder{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogConsumerHolder.class);

    private List<SysLogConsumer> sysLogConsumers = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void register(String consumerId, ChannelHandlerContext ctx) {
        boolean exist = false;
        for (SysLogConsumer sysLogConsumer : sysLogConsumers) {
            //同一个消费者，分布式系统中多个结点注册同一个消费者
            if (consumerId.equals(sysLogConsumer.getConsumerId())) {
                exist = true;
                sysLogConsumer.register(ctx);
                break;
            }
        }
        if (!exist) {
            sysLogConsumers.add(new SysLogConsumer(consumerId, ctx));
        }
        LOGGER.info("系统日志消费者注册成功， consumerId={}, messageType={}", consumerId, MessageType.SYS_LOG);
    }

    @Override
    public void unregister(String consumerId, ChannelHandlerContext ctx) {
        for (SysLogConsumer sysLogConsumer : sysLogConsumers) {
            if(sysLogConsumer.getConsumerId().equals(consumerId)) {
                List<ChannelHandlerContext> ctxs = sysLogConsumer.getCtxs();
                for (ChannelHandlerContext handlerContext : ctxs) {
                    if(handlerContext.equals(ctx)) {
                        ctxs.remove(ctx);
                        LOGGER.info("系统日志消费者注销成功， consumerId={}, messageType={}", consumerId, MessageType.SYS_LOG);
                        return;
                    }
                }
            }
        }
    }

    public List<SysLogConsumer> getSysLogConsumers() {
        return sysLogConsumers;
    }
}
