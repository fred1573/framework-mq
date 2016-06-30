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
 * 消息中心消费者持有
 * @author frd
 *         2016/6/27.
 */

@Component
public class BizMessageConsumerHolder implements IConsumerHolder{

    private static final Logger LOGGER = LoggerFactory.getLogger(BizMessageConsumerHolder.class);

    private List<BizMessageConsumer> bizMessageConsumers = Collections.synchronizedList(new ArrayList<>());

    public void register(String consumerId, ChannelHandlerContext ctx) {
        boolean exist = false;
        for (BizMessageConsumer bizMessageConsumer : bizMessageConsumers) {
            //同一个消费者，分布式系统中多个结点注册同一个消费者
            if (consumerId.equals(bizMessageConsumer.getConsumerId())) {
                exist = true;
                bizMessageConsumer.register(ctx);
                break;
            }
        }
        if (!exist) {
            bizMessageConsumers.add(new BizMessageConsumer(consumerId, ctx));
        }
        LOGGER.info("消息中心消费者注册成功， consumerId={}, messageType={}", consumerId, MessageType.BIZ_MESSAGE);
    }

    /**
     * 注销
     * @param consumerId
     */
    public void unregister(String consumerId, ChannelHandlerContext ctx) {
        for (BizMessageConsumer bizMessageConsumer : bizMessageConsumers) {
            if(bizMessageConsumer.getConsumerId().equals(consumerId)) {
                List<ChannelHandlerContext> ctxs = bizMessageConsumer.getCtxs();
                for (ChannelHandlerContext handlerContext : ctxs) {
                    if(handlerContext.equals(ctx)) {
                        ctxs.remove(ctx);
                        LOGGER.info("消息中心消费者注销成功， consumerId={}, messageType={}", consumerId, MessageType.BIZ_MESSAGE);
                        return;
                    }
                }
            }
        }
    }

    public List<BizMessageConsumer> getBizMessageConsumers() {
        return bizMessageConsumers;
    }
}
