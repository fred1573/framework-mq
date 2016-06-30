package com.tomasky.framework.mc.persistence.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Hunhun
 *         2015-12-02 10:32
 */
@Document(collection = "consumer")
public class PersisConsumer {

    @Id
    private String id;
    private String messageType;
    private String consumerId;
    private LocalDateTime firstTime = LocalDateTime.now();
    private LocalDateTime lastTime = LocalDateTime.now();

    public PersisConsumer(String messageType, String consumerId) {
        this.messageType = messageType;
        this.consumerId = consumerId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(LocalDateTime firstTime) {
        this.firstTime = firstTime;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PersisConsumer)) {
            return false;
        }
        PersisConsumer consumer = (PersisConsumer) obj;
        String consumerId = consumer.getConsumerId();
        String messageType = consumer.getMessageType();
        return consumerId.equals(getConsumerId()) && messageType.equals(getMessageType());
    }

}
