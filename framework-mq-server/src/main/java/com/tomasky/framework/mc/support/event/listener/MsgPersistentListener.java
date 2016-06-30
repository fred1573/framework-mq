package com.tomasky.framework.mc.support.event.listener;

import com.tomasky.framework.mc.persistence.MessagePersistence;
import com.tomasky.framework.mc.persistence.mongo.model.PersistenceEntity;
import com.tomasky.framework.mc.support.event.model.MsgPersitentEvent;
import com.tomato.mq.support.core.AbstractMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Hunhun
 */
@Component
public class MsgPersistentListener implements ApplicationListener<MsgPersitentEvent> {

    @Autowired
    private MessagePersistence messagePersistence;

    @Override
    public void onApplicationEvent(MsgPersitentEvent event) {
        messagePersistence.persistence(new PersistenceEntity((AbstractMessage) event.getSource()));
    }
}
