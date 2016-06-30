package com.tomasky.framework.mc.persistence;

import com.tomasky.framework.mc.persistence.mongo.model.CacheEntity;
import com.tomasky.framework.mc.persistence.mongo.model.PersisConsumer;

import java.util.List;

/**
 * 消息持久化
 * @author Hunhun
 *         14:03
 */
public interface MessagePersistence {

    /**
     * update
     */
    void updatePersisConsumer(String messageType, String consumerId);

    /**
     * 持久化所有传入到消息服务中的信息
     * @param message
     */
    void persistence(Object message);

    /**
     * 缓存未被消费的非事件类型消息
     * @param cacheEntity
     */
    void cacheNonSysEventMsg(CacheEntity cacheEntity);

    /**
     * 缓存未被消费的事件类型消息
     * @param cacheEntity
     */
    void cacheSysEventMsg(CacheEntity cacheEntity);

    /**
     * 加载所有的被持久化的消费者
     * @return
     */
    List<PersisConsumer> loadPersisConsumer();


}
