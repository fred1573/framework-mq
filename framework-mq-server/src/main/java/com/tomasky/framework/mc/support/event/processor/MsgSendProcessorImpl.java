package com.tomasky.framework.mc.support.event.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomasky.framework.mc.netty.model.MsgSend;
import com.tomasky.framework.mc.producer.MQClient;
import com.tomasky.framework.mc.support.event.model.MsgPersitentEvent;
import com.tomasky.framework.mc.support.event.publisher.IMessageEventPublisher;
import com.tomato.mq.support.core.AbstractMessage;
import com.tomato.mq.support.core.MessageDomain;
import com.tomato.mq.support.core.SysMessage;
import com.tomato.mq.support.core.TextMessage;
import com.tomato.mq.support.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Hunhun
 *         2015/12/4
 */
@Component("msgSendProcessor")
public class MsgSendProcessorImpl implements MsgSendProcessor {

    @Autowired
    private MQClient mqClient;
    @Autowired
    private IMessageEventPublisher eventPublisher;

    @Override
    public void sendToMOM(MsgSend msgSend) {
        String message = msgSend.getSignal().getMessage();
        JSONObject jsonObject = JSON.parseObject(message);
        AbstractMessage abstractMessage;
        if (jsonObject.get("messageType").equals(MessageType.SYS_EVENT.toString())) {
            abstractMessage = JSON.parseObject(message, SysMessage.class);
        } else {
            abstractMessage = JSON.parseObject(message, TextMessage.class);
        }
        eventPublisher.publishEvent(new MsgPersitentEvent(abstractMessage));
        mqClient.send(abstractMessage, MessageDomain.QUEUE);
    }
}
