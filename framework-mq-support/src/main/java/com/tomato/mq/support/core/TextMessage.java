package com.tomato.mq.support.core;

import com.tomato.mq.support.message.MessageType;

import java.util.Date;

/**
 * @author Hunhun
 *         14:26
 */
public class TextMessage extends AbstractMessage {

    private String clientId;

    public TextMessage() {
    }

    public TextMessage(String content, MessageType messageType, String clientIden) {
        setContent(content);
        setMessageType(messageType);
        setDate(new Date());
        setClientId(clientIden);
    }

    private void setClientId(String clientIden){
        this.clientId = clientIden;
    }

    public String getClientId() {
        return clientId;
    }

    public String fakeHashCode() {
        return String.valueOf(System.currentTimeMillis()) + ":" + super.hashCode();
    }
}
