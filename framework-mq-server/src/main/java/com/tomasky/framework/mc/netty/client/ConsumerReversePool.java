package com.tomasky.framework.mc.netty.client;

import com.tomasky.framework.mc.support.consumer.BizLogConsumerHolder;
import com.tomasky.framework.mc.support.consumer.BizMessageConsumerHolder;
import com.tomasky.framework.mc.support.consumer.ConsumerReverse;
import com.tomasky.framework.mc.support.consumer.SysEventConsumerHolder;
import com.tomasky.framework.mc.support.consumer.SysLogConsumerHolder;
import com.tomato.mq.support.message.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 消费者与通道的关系反转
 *
 * @author Hunhun
 */
@Component
public class ConsumerReversePool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerReversePool.class);

    private Map<ChannelHandlerContext, ConsumerReverse> consumerReversePools = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private BizLogConsumerHolder bizLogConsumerHolder;
    @Autowired
    private BizMessageConsumerHolder bizMessageConsumerHolder;
    @Autowired
    private SysEventConsumerHolder sysEventConsumerHolder;
    @Autowired
    private SysLogConsumerHolder sysLogConsumerHolder;

    public void add(ChannelHandlerContext ctx, String consumerId, MessageType messageType) {
        if (consumerReversePools.containsKey(ctx)) {
            ConsumerReverse consumerReverse = consumerReversePools.get(ctx);
            consumerReverse.getMsgTypes().add(messageType);
        } else {
            consumerReversePools.put(ctx, new ConsumerReverse(consumerId, messageType));
        }
    }

    public void remove(ChannelHandlerContext ctx) {
        if (consumerReversePools.containsKey(ctx)) {
            ConsumerReverse consumerReverse = consumerReversePools.get(ctx);
            Set<MessageType> msgTypes = consumerReverse.getMsgTypes();
            for (MessageType msgType : msgTypes) {
                if (msgType.equals(MessageType.BIZ_LOG)) {
                    bizLogConsumerHolder.unregister(consumerReverse.getConsumerId(), ctx);
                } else if (msgType.equals(MessageType.BIZ_MESSAGE)) {
                    bizMessageConsumerHolder.unregister(consumerReverse.getConsumerId(), ctx);
                } else if (msgType.equals(MessageType.SYS_EVENT)) {
                    sysEventConsumerHolder.unregister(consumerReverse.getConsumerId(), ctx);
                } else if (msgType.equals(MessageType.SYS_LOG)) {
                    sysLogConsumerHolder.unregister(consumerReverse.getConsumerId(), ctx);
                } else {
                    LOGGER.warn("there is a messageType which can not be recognized when remove a consumer");
                }
            }
            consumerReversePools.remove(ctx);
        }
    }
}
