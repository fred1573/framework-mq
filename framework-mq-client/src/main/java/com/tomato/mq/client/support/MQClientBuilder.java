package com.tomato.mq.client.support;

import com.tomato.mq.client.produce.MQProducer;
import com.tomato.mq.client.produce.MQProducerImpl;

/**
 * @author Hunhun
 *         2015-09-14 12:00
 */
public class MQClientBuilder {

    private static MQProducer instance = null;

    public static MQProducer build(){
        if(instance == null){
            synchronized (MQClientBuilder.class){
                if(instance == null){
                    instance = new MQProducerImpl();
                }
            }
        }
        return  instance;
    }
}
