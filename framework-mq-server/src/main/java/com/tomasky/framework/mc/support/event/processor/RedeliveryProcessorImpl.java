package com.tomasky.framework.mc.support.event.processor;

import com.tomasky.framework.mc.persistence.mongo.model.CacheEntity;
import com.tomato.mq.support.message.MessageType;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hunhun
 *         2015-09-10-18:05
 */
@Component
public class RedeliveryProcessorImpl implements RedeliveryProcessor {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void redeliveryMsg(MessageType messageType, String consumerId, ChannelHandlerContext ctx) {
        List<CacheEntity> cacheEntities = mongoTemplate.find(new Query(Criteria.where("messageType").is(messageType.toString()).andOperator(Criteria.where("consumerId").is(consumerId))), CacheEntity.class);
        redelivery(messageType, ctx, cacheEntities);
    }

    private void redelivery(MessageType messageType, ChannelHandlerContext ctx, List<CacheEntity> cacheEntities) {
        for (CacheEntity cacheEntity : cacheEntities) {
            ctx.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, cacheEntity.getContent().toString(), messageType));
            mongoTemplate.remove(new Query(Criteria.where("id").is(cacheEntity.getId())), CacheEntity.class);
        }
    }

}
