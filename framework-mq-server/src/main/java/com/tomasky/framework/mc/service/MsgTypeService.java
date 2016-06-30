package com.tomasky.framework.mc.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.tomasky.framework.mc.controller.vo.MsgTypeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by  idea
 * user: yutaoxun
 * data: 2016/5/5
 * email: yutaoxun@gmail.com
 * company: fanqielaile
 * description: 消息类型服务类，主要是用于查找消息的类型.
 */
@Service
@Transactional
public class MsgTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(MsgTypeService.class);

    private static final String MSG_TYPE= "msgType";
    private static final String MESSAGE_TYPE = "message_type";

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 删除之前错误录入的消息类型描述.
     * @param msgType
     */
    public void delete(String msgType){
        BasicDBObject query = new BasicDBObject(MSG_TYPE,msgType);
        mongoTemplate.getCollection(MESSAGE_TYPE).remove(query);
    }

    /**
     * 新增消息描述说明.
     * @param mtv
     */
    public boolean save(MsgTypeVo mtv){
        Map result = findOne(mtv.getMsgType());
        if(null != result && !result.isEmpty()){
            LOG.info("改条记录已经在数据库中存在，请不要重复保存!");
            return false;
        }
        mtv.setCreateTime(new Date());
        mongoTemplate.save(mtv,MESSAGE_TYPE);
        return true;
    }

    /**
     * 查找指定类型的消息类型描述.
     * @param msgType
     */
    public Map findOne(String msgType){
        LOG.debug("查找指定类型的消息类型描述^^!");
        BasicDBObject query = new BasicDBObject(MSG_TYPE,msgType);
        DBObject result = mongoTemplate.getCollection(MESSAGE_TYPE).findOne(query);
        return null != result ? result.toMap():new HashMap();
    }


    /**
     * 查找所有的消息类型.
     */
    public List<Map> findAll(){
        DBCursor cursor = mongoTemplate.getCollection(MESSAGE_TYPE).find();
        if(null == cursor || !cursor.hasNext()){
            return new ArrayList<Map>();
        }
        List<Map> result = new ArrayList<Map>();
        while(cursor.hasNext()){
            DBObject dbo = cursor.next();
            result.add(dbo.toMap());
        }
        return result;
    }
}
