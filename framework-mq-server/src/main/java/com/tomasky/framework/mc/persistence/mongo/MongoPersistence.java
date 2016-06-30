package com.tomasky.framework.mc.persistence.mongo;

import com.tomasky.framework.mc.persistence.MessagePersistence;
import com.tomasky.framework.mc.persistence.mongo.model.CacheEntity;
import com.tomasky.framework.mc.persistence.mongo.model.PersisConsumer;
import com.tomasky.framework.mc.persistence.mongo.model.PersistenceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hunhun
 *         14:09
 */
@Component("mongoPersistence")
public class MongoPersistence implements MessagePersistence {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updatePersisConsumer(String messageType, String consumerId) {
        mongoTemplate.updateFirst(new Query(Criteria.where("messageType").is(messageType).andOperator(Criteria.where("consumerId").is(consumerId))), new Update().set("lastTime", LocalDateTime.now()), PersisConsumer.class);
    }

    @Override
    public void persistence(Object message) {
        mongoTemplate.save(message);
    }

    @Override
    public void cacheNonSysEventMsg(CacheEntity cacheEntity) {
        mongoTemplate.save(cacheEntity);
    }

    @Override
    public List<PersisConsumer> loadPersisConsumer() {
        return mongoTemplate.find(new Query(), PersisConsumer.class);
    }

    public void cacheSysEventMsg(CacheEntity cacheEntity) {
        mongoTemplate.save(cacheEntity);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
        mongoTemplate.find(new Query(Criteria.where("persisTime").gte(LocalDateTime.of(2015,12,14,8,8,8))), PersistenceEntity.class);
        System.out.println("1");
    }
}
