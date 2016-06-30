package com.tomasky.framework.mc.listener;

import com.tomasky.framework.mc.support.event.model.MsgPushEvent;
import com.tomasky.framework.mc.support.event.publisher.IMessageEventPublisher;
import com.tomato.mq.support.core.AbstractMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;

/**
 * @author Simon
 */
public class AmqTextMessageListener implements MessageListener {

    @Resource
    protected IMessageEventPublisher eventPublisher;


    @Override
    public void onMessage(Message message) {

        if (message instanceof ActiveMQObjectMessage) {
            try {
                Serializable serializable = ((ActiveMQObjectMessage) message).getObject();
                AbstractMessage abstractMessage = (AbstractMessage) serializable;
                doWithMessage(abstractMessage);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void doWithMessage(AbstractMessage iMessage) {
        eventPublisher.publishEvent(new MsgPushEvent(iMessage));
    }

}
