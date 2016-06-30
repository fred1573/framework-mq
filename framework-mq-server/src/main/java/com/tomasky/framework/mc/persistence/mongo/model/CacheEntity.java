package com.tomasky.framework.mc.persistence.mongo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomato.mq.support.core.AbstractMessage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Hunhun
 *         2015-12-01 16:57
 */
@Document(collection = "tomato_cache_entity")
public class CacheEntity {

    @Id
    private String id;

    private String consumerId;

    private String messageType;

    private JSONObject content;

    private LocalDateTime cacheTime = LocalDateTime.now();

    public CacheEntity() {
    }

    public CacheEntity(AbstractMessage message) {
        this.content = JSON.parseObject(message.getContent());
        this.consumerId = message.getConsumerId();
        this.messageType = message.getMessageType().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }

    public LocalDateTime getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(LocalDateTime cacheTime) {
        this.cacheTime = cacheTime;
    }
}
