package com.tomasky.framework.mc.support;

import com.tomasky.framework.mc.producer.AMQClient;
import com.tomasky.framework.mc.producer.MQClient;

import java.util.Objects;

/**
 * MQClient 工厂
 * Created by Administrator on 2015/4/20.
 */
public class MQClientBuilder {

    public static final Integer MQ_AMQ = 1;

    private Integer mq;

    public MQClient build() throws Exception {
        MQClient client;
        if (Objects.equals(mq, MQ_AMQ)){
            client = new AMQClient();
        }else{
            throw new Exception("mqclient cannot be created,mq type:" + mq);
        }
        return client;
    }

    public void setMq(Integer mq) {
        this.mq = mq;
    }
}
