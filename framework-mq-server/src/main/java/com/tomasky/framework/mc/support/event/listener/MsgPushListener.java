package com.tomasky.framework.mc.support.event.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomasky.framework.mc.persistence.mongo.MongoPersistence;
import com.tomasky.framework.mc.persistence.mongo.model.CacheEntity;
import com.tomasky.framework.mc.persistence.mongo.model.PersisConsumer;
import com.tomasky.framework.mc.support.PersisConsumerHolder;
import com.tomasky.framework.mc.support.consumer.BizLogConsumer;
import com.tomasky.framework.mc.support.consumer.BizLogConsumerHolder;
import com.tomasky.framework.mc.support.consumer.BizMessageConsumer;
import com.tomasky.framework.mc.support.consumer.BizMessageConsumerHolder;
import com.tomasky.framework.mc.support.consumer.SysEventConsumer;
import com.tomasky.framework.mc.support.consumer.SysEventConsumerHolder;
import com.tomasky.framework.mc.support.consumer.SysLogConsumer;
import com.tomasky.framework.mc.support.consumer.SysLogConsumerHolder;
import com.tomasky.framework.mc.support.event.model.MsgPushEvent;
import com.tomato.mq.support.core.AbstractMessage;
import com.tomato.mq.support.core.SysMessage;
import com.tomato.mq.support.message.MessageType;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Hunhun
 *         2015-11-30 15:31
 */
@Component
public class MsgPushListener implements ApplicationListener<MsgPushEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgPushListener.class);

    @Autowired
    private MongoPersistence mongoPersistence;
    @Autowired
    private PersisConsumerHolder persisConsumerHolder;
    @Autowired
    private BizLogConsumerHolder bizLogConsumerHolder;
    @Autowired
    private BizMessageConsumerHolder bizMessageConsumerHolder;
    @Autowired
    private SysEventConsumerHolder sysEventConsumerHolder;
    @Autowired
    private SysLogConsumerHolder sysLogConsumerHolder;

    @Override
    public void onApplicationEvent(MsgPushEvent event) {
        AbstractMessage iMessage = (AbstractMessage) event.getSource();
        push(iMessage);
    }

    private void push(AbstractMessage message) {
        LOGGER.debug("thread:{}, message:{}", Thread.currentThread().getName(), JSON.toJSONString(message));
        MessageType messageType = message.getMessageType();
        if (messageType.equals(MessageType.BIZ_LOG)) {
            pushBizLog(message);
        } else if (messageType.equals(MessageType.BIZ_MESSAGE)) {
            pushBizMessage(message);
        } else if (messageType.equals(MessageType.SYS_EVENT)) {
            pushSysEvent(message);
        } else if (messageType.equals(MessageType.SYS_LOG)) {
            pushSysLog(message);
        } else {
            throw new RuntimeException("消息类型无法识别");
        }
    }

    private void pushSysLog(AbstractMessage message) {
        List<PersisConsumer> persisConsumers = persisConsumerHolder.getConsumersByMessageType(MessageType.SYS_LOG);
        List<SysLogConsumer> sysLogConsumers = sysLogConsumerHolder.getSysLogConsumers();
        if (CollectionUtils.isEmpty(sysLogConsumers)) {
            cacheAll(message, persisConsumers);
            return;
        }
        for (SysLogConsumer sysLogConsumer : sysLogConsumers) {
            ChannelHandlerContext channelHandlerContext = sysLogConsumer.vote();
            if (channelHandlerContext != null) {
                channelHandlerContext.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, convert(message).toJSONString(), MessageType.SYS_LOG));
            } else {
                message.setConsumerId(sysLogConsumer.getConsumerId());
                mongoPersistence.cacheSysEventMsg(new CacheEntity(message));
            }
        }
    }

    private void pushSysEvent(AbstractMessage message) {
        List<PersisConsumer> persisConsumers = persisConsumerHolder.getConsumersByMessageType(MessageType.SYS_EVENT);
        List<SysEventConsumer> sysEventConsumers = sysEventConsumerHolder.getSysEventConsumers();
        if (CollectionUtils.isEmpty(sysEventConsumers)) {
            cacheAll(message, persisConsumers);
            return;
        }
        for (SysEventConsumer sysEventConsumer : sysEventConsumers) {
            ChannelHandlerContext channelHandlerContext = sysEventConsumer.vote();
            if (channelHandlerContext != null) {
                channelHandlerContext.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, convert(message).toJSONString(), MessageType.SYS_EVENT));
            } else {
                message.setConsumerId(sysEventConsumer.getConsumerId());
                mongoPersistence.cacheSysEventMsg(new CacheEntity(message));
            }
        }
    }

    private void pushBizMessage(AbstractMessage message) {
        List<PersisConsumer> persisConsumers = persisConsumerHolder.getConsumersByMessageType(MessageType.BIZ_MESSAGE);
        List<BizMessageConsumer> bizMessageConsumers = bizMessageConsumerHolder.getBizMessageConsumers();
        if (CollectionUtils.isEmpty(bizMessageConsumers)) {
            cacheAll(message, persisConsumers);
            return;
        }
        for (BizMessageConsumer bizMessageConsumer : bizMessageConsumers) {
            ChannelHandlerContext channelHandlerContext = bizMessageConsumer.vote();
            if (channelHandlerContext != null) {
                channelHandlerContext.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, convert(message).toJSONString(), MessageType.BIZ_MESSAGE));
            } else {
                message.setConsumerId(bizMessageConsumer.getConsumerId());
                mongoPersistence.cacheSysEventMsg(new CacheEntity(message));
            }
        }
    }

    private void pushBizLog(AbstractMessage message) {
        List<PersisConsumer> persisConsumers = persisConsumerHolder.getConsumersByMessageType(MessageType.BIZ_LOG);
        List<BizLogConsumer> bizLogConsumers = bizLogConsumerHolder.getBizLogConsumers();
        if (CollectionUtils.isEmpty(bizLogConsumers)) {
            //一个活跃的消费者都没有的情况下
            cacheAll(message, persisConsumers);
            return;
        }
        for (BizLogConsumer bizLogConsumer : bizLogConsumers) {
            ChannelHandlerContext channelHandlerContext = bizLogConsumer.vote();
            if (channelHandlerContext != null) {
                channelHandlerContext.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, convert(message).toJSONString(), MessageType.BIZ_LOG));
            } else {
                message.setConsumerId(bizLogConsumer.getConsumerId());
                mongoPersistence.cacheSysEventMsg(new CacheEntity(message));
            }
        }
    }

    private void cacheAll(AbstractMessage message, List<PersisConsumer> persisConsumers) {
        for (PersisConsumer persisConsumer : persisConsumers) {
            message.setConsumerId(persisConsumer.getConsumerId());
            mongoPersistence.cacheSysEventMsg(new CacheEntity(message));
        }
    }

    private JSONObject convert(AbstractMessage message) {
        MessageType messageType = message.getMessageType();
        JSONObject json = new JSONObject();
        json.put("content", message.getContent());
        if (messageType.equals(MessageType.SYS_EVENT)) {
            SysMessage sysMessage = (SysMessage) message;
            json.put("projectName", sysMessage.getProjectName());
            json.put("bizType", sysMessage.getBizType());
            return JSON.parseObject(json.toJSONString());
        }
        return JSON.parseObject(message.getContent());
    }
}
