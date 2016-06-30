package com.tomasky.framework.mc.support.event.listener;

import com.tomasky.framework.mc.support.event.model.RedeliveryEvent;
import com.tomasky.framework.mc.support.event.processor.RedeliveryProcessor;
import com.tomasky.framework.mc.support.redelivery.model.Redelivery;
import com.tomato.mq.support.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Hunhun
 *         2015-12-03 10:19
 */
@Component
public class RedeliveryListener implements ApplicationListener<RedeliveryEvent> {

    @Autowired
    private RedeliveryProcessor redeliveryService;

    @Override
    public void onApplicationEvent(RedeliveryEvent event) {
        Redelivery redelivery = (Redelivery) event.getSource();
        Set<MessageType> messageTypes = redelivery.getMessageTypes();
        for (MessageType messageType : messageTypes) {
            redeliveryService.redeliveryMsg(messageType, redelivery.getConsumerId(), redelivery.getCtx());
        }
    }
}
