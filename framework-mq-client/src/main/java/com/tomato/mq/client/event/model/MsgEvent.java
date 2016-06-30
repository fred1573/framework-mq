package com.tomato.mq.client.event.model;

import com.tomato.mq.support.message.MessageType;

import java.util.EventObject;

/**
 * @author Hunhun
 *         2015-09-11-14:29
 */
public class MsgEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    private MessageType messageType;

    public MsgEvent(Object source) {
        super(source);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
