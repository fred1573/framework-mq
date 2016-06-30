package com.tomasky.framework.mc.persistence.mongo;

import com.mongodb.DBObject;
import com.tomasky.framework.mc.persistence.mongo.model.PersistenceEntity;
import com.tomasky.framework.mc.support.LocalDateTimeUtils;
import com.tomasky.framework.mc.support.bo.ConcretePersis;
import com.tomasky.framework.mc.support.bo.SimplePersis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Hunhun
 *         2015-12-01 17:33
 */
@Component
public class MongoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoUtils.class);

    public List<SimplePersis> wrapSimplePersises(List<PersistenceEntity> pes) {
        List<SimplePersis> results = new ArrayList<>();
        for (PersistenceEntity pe : pes) {
            results.add(wrapSimplePersis(pe));
        }
        return results;
    }

    private SimplePersis wrapSimplePersis(Object object) {
        SimplePersis sp = new SimplePersis();
        if(object instanceof PersistenceEntity) {
            PersistenceEntity pe = (PersistenceEntity) object;
            BeanUtils.copyProperties(pe, sp);
            sp.setMsgType(pe.getMessageType());
        }else if(object instanceof DBObject){
            DBObject dbObject = (DBObject) object;
            sp.setId(dbObject.get("_id").toString());
            sp.setMsgType(dbObject.get("messageType").toString());
            sp.setPersisTime(LocalDateTimeUtils.getLocalDateTime(dbObject.get("persisTime")));
        }else{
            String error = "封装simplePersis时入参类型无法解析, class:" + object.getClass().getName();
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        return sp;
    }

    public ConcretePersis wrapConcretePersis(PersistenceEntity entity) {
        ConcretePersis cp = new ConcretePersis();
        cp.setSimplePersis(wrapSimplePersis(entity));
        cp.setContent(entity.getContent());
        return cp;
    }

    public Query composeQuery(Map<String, Object> params){
        Criteria criteria = new Criteria();
        Criteria[] criterias = new Criteria[params.size()];
        int i = 0;
        for (String key : params.keySet()) {
            if(key.equals("persisFrom")){
                criterias[i] = Criteria.where("persisTime").gte(params.get(key));
            }else if(key.equals("persisTo")){
                criterias[i] = Criteria.where("persisTime").lt(params.get(key));
            }else{
                criterias[i] = Criteria.where(key).is(params.get(key));
            }
            i++;
        }
        criteria.andOperator(criterias);
        return new Query(criteria);
    }

}
