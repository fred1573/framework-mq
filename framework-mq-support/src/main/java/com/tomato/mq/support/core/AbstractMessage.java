package com.tomato.mq.support.core;

import com.tomato.mq.support.message.MessageType;

import java.io.Serializable;
import java.util.Date;

/**
 * 自定义消息
 * @author Hunhun
 *         14:16
 */
public abstract class AbstractMessage implements Serializable {

    private MessageType messageType;
    private Date date;
    private String content;
    private String consumerId;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
