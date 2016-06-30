package com.tomasky.framework.mc.support;

import com.tomasky.framework.mc.persistence.MessagePersistence;
import com.tomasky.framework.mc.persistence.mongo.model.PersisConsumer;
import com.tomato.mq.support.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Hunhun
 *         2015-12-02 11:03
 */
@Component
public class PersisConsumerHolder {

    private List<PersisConsumer> consumers = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private MessagePersistence messagePersistence;

    public PersisConsumerHolder() {

    }

    @PostConstruct
    public void init(){
        consumers = messagePersistence.loadPersisConsumer();
    }

    public List<PersisConsumer> getConsumersByMessageType(MessageType messageType) {
        List<PersisConsumer> result = new ArrayList<>();
        for (PersisConsumer consumer : consumers) {
            if(consumer.getMessageType().equals(messageType.toString())){
                result.add(consumer);
            }
        }
        return result;
    }

    public void addConsumer(MessageType messageType, String consumerId) {
        PersisConsumer persisConsumer = new PersisConsumer(messageType.toString(), consumerId);
        boolean anyMatch = consumers.stream().anyMatch(persisConsumer1 -> persisConsumer1.equals(persisConsumer));
        if(!anyMatch){
            messagePersistence.persistence(persisConsumer);
            consumers.add(persisConsumer);
        }else{
            messagePersistence.updatePersisConsumer(messageType.toString(), consumerId);
        }
    }

    public void addConsumer(Set<MessageType> messageTypes, String consumerId) {
        messageTypes.forEach(messageType -> addConsumer(messageType,consumerId));
    }
}
