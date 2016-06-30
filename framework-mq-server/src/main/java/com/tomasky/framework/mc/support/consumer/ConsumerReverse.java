package com.tomasky.framework.mc.support.consumer;

import com.tomato.mq.support.message.MessageType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 消费者反向查找bean，用于channel断开连接后从consumerHolder中通过ctx清除consumer，
 * @author frd
 *         2016/6/28.
 */
public class ConsumerReverse {

    /**
     * channel断开连接后要清除的消费者及对应的消息类型
     */
    private Set<MessageType> msgTypes = Collections.synchronizedSet(new HashSet<>());
    private String consumerId;

    public ConsumerReverse(String consumerId, MessageType messageType) {
        this.consumerId = consumerId;
        addMessageType(messageType);
    }

    public void addMessageType(MessageType messageType) {
        msgTypes.add(messageType);
    }

    public Set<MessageType> getMsgTypes() {
        return msgTypes;
    }

    public String getConsumerId() {
        return consumerId;
    }
}
