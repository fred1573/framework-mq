package com.tomasky.framework.mc.persistence.mongo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomato.mq.support.core.AbstractMessage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Hunhun
 *         2015-09-10-15:47
 */
@Document(collection = "tomato_persistence_entity")
public class PersistenceEntity {

    @Id
    private String id;

    private JSONObject content;

    private String messageType;

    private LocalDateTime persisTime = LocalDateTime.now();

    public PersistenceEntity() {
    }

    public PersistenceEntity(AbstractMessage message) {
        this.content = JSON.parseObject(JSON.toJSONString(message));
        content.put("content", JSON.parseObject(message.getContent()));
        this.messageType = message.getMessageType().toString();
    }

    public String getId() {
        return id;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getPersisTime() {
        return persisTime;
    }

    public void setPersisTime(LocalDateTime persisTime) {
        this.persisTime = persisTime;
    }
}
