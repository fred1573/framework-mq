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
 *         2016/6/27.
 */

@Component
public class BizLogConsumerHolder implements IConsumerHolder{

    private static final Logger LOGGER = LoggerFactory.getLogger(BizLogConsumerHolder.class);

    private List<BizLogConsumer> bizLogConsumers = Collections.synchronizedList(new ArrayList<>());

    public void register(String consumerId, ChannelHandlerContext ctx) {
        boolean exist = false;
        for (BizLogConsumer bizLogConsumer : bizLogConsumers) {
            //同一个消费者，分布式系统中多个结点注册同一个消费者
            if (consumerId.equals(bizLogConsumer.getConsumerId())) {
                exist = true;
                bizLogConsumer.register(ctx);
                break;
            }
        }
        if (!exist) {
            bizLogConsumers.add(new BizLogConsumer(consumerId, ctx));
        }
        LOGGER.info("业务日志消费者注册成功， consumerId={}, messageType={}", consumerId, MessageType.BIZ_LOG);
    }

    /**
     * 注销
     * @param consumerId
     */
    public void unregister(String consumerId, ChannelHandlerContext ctx) {
        for (BizLogConsumer bizLogConsumer : bizLogConsumers) {
            if(bizLogConsumer.getConsumerId().equals(consumerId)) {
                List<ChannelHandlerContext> ctxs = bizLogConsumer.getCtxs();
                for (ChannelHandlerContext handlerContext : ctxs) {
                    if(handlerContext.equals(ctx)) {
                        ctxs.remove(ctx);
                        LOGGER.info("业务日志消费者注销成功， consumerId={}, messageType={}", consumerId, MessageType.BIZ_LOG);
                        return;
                    }
                }
            }
        }
    }

    public List<BizLogConsumer> getBizLogConsumers() {
        return bizLogConsumers;
    }
}
