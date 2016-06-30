package com.tomasky.framework.mc.support.consumer;

import com.tomato.mq.support.message.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Hunhun
 *         2015-10-20 20:30
 */
@Component
public class ConsumerHolderBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerHolderBuilder.class);
    @Autowired
    private BizLogConsumerHolder bizLogConsumerHolder;
    @Autowired
    private BizMessageConsumerHolder bizMessageConsumerHolder;
    @Autowired
    private SysEventConsumerHolder sysEventConsumerHolder;
    @Autowired
    private SysLogConsumerHolder sysLogConsumerHolder;

    /**
     * 注册消费者
     */
    public void register(String consumerId, MessageType messageType, ChannelHandlerContext ctx) {
        if (messageType.equals(MessageType.BIZ_LOG)) {
            bizLogConsumerHolder.register(consumerId, ctx);
        }else if(messageType.equals(MessageType.BIZ_MESSAGE)) {
            bizMessageConsumerHolder.register(consumerId, ctx);
        } else if(messageType.equals(MessageType.SYS_EVENT)) {
            sysEventConsumerHolder.register(consumerId, ctx);
        } else if(messageType.equals(MessageType.SYS_LOG)) {
            sysLogConsumerHolder.register(consumerId, ctx);
        } else {
            LOGGER.warn("注册消费者逮到一个不能识别的消息类型, messageType={}, consumerId={}", messageType, consumerId);
        }
    }


}
