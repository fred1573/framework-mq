package com.tomasky.framework.mc.support.redelivery.model;

import com.tomato.mq.support.message.MessageType;
import io.netty.channel.ChannelHandlerContext;

import java.util.Set;

/**
 * @author Hunhun
 *         2015-12-03 10:11
 */
public class Redelivery {

    private Set<MessageType> messageTypes;
    private String consumerId;
    private ChannelHandlerContext ctx;

    public Redelivery(Set<MessageType> messageTypes, String consumerId, ChannelHandlerContext ctx) {
        this.messageTypes = messageTypes;
        this.consumerId = consumerId;
        this.ctx = ctx;
    }

    public Set<MessageType> getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(Set<MessageType> messageTypes) {
        this.messageTypes = messageTypes;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
