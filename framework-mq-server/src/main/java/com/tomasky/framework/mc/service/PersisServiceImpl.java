package com.tomasky.framework.mc.service;

import com.tomasky.framework.mc.persistence.mongo.MongoUtils;
import com.tomasky.framework.mc.persistence.mongo.model.PersistenceEntity;
import com.tomasky.framework.mc.support.bo.ConcretePersis;
import com.tomasky.framework.mc.support.bo.Page;
import com.tomasky.framework.mc.support.bo.SimplePersis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Hunhun
 */
@Service
@Transactional
public class PersisServiceImpl implements PersisService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoUtils mongoUtils;

    @Override
    public Page<SimplePersis> load(Map<String, Object> params, Page<SimplePersis> page) {

        Integer pageSize = page.getPageSize();
        Query query = mongoUtils.composeQuery(params);
        query = query.with(new Sort(Sort.DEFAULT_DIRECTION, "persisTime")).skip((page.getPageIndex()) * pageSize).limit(pageSize);
        List<PersistenceEntity> persistenceEntities = mongoTemplate.find(query, PersistenceEntity.class);

//        DBObject fields = new BasicDBObject();
//        fields.put("messageType", 1);
//        fields.put("persisTime", 1);
        page.setResult(mongoUtils.wrapSimplePersises(persistenceEntities));
        page.setTotalCount(totalCount(params));
        return page;
    }

    @Override
    public Long totalCount(Map<String, Object> params) {
        Query query = mongoUtils.composeQuery(params);
        return mongoTemplate.count(query, PersistenceEntity.class);
    }

    @Override
    public ConcretePersis findById(String id) {
        PersistenceEntity entity = mongoTemplate.findById(id, PersistenceEntity.class);
        if (entity != null) {
            return mongoUtils.wrapConcretePersis(entity);
        }
        return null;
    }

}
