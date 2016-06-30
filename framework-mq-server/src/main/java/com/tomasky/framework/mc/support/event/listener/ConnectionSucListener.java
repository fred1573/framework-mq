package com.tomasky.framework.mc.support.event.listener;

import com.tomasky.framework.mc.netty.client.ConsumerReversePool;
import com.tomasky.framework.mc.netty.model.ConnectionSuccess;
import com.tomasky.framework.mc.support.PersisConsumerHolder;
import com.tomasky.framework.mc.support.consumer.ConsumerHolderBuilder;
import com.tomasky.framework.mc.support.event.model.ConnectionSucEvent;
import com.tomasky.framework.mc.support.event.model.RedeliveryEvent;
import com.tomasky.framework.mc.support.event.publisher.IMessageEventPublisher;
import com.tomasky.framework.mc.support.redelivery.model.Redelivery;
import com.tomato.mq.support.message.MessageType;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @author Hunhun
 */
@Component
public class ConnectionSucListener implements ApplicationListener<ConnectionSucEvent> {

    @Autowired
    private IMessageEventPublisher eventPublisher;
    @Autowired
    private PersisConsumerHolder persisConsumerHolder;
    @Autowired
    private ConsumerHolderBuilder consumerHolderBuilder;
    @Autowired
    private ConsumerReversePool consumerReversePool;

    @Override
    public void onApplicationEvent(ConnectionSucEvent event) {
        ConnectionSuccess source = (ConnectionSuccess) event.getSource();
        if(!validate(source)){
            return;
        }
        ChannelHandlerContext ctx = source.getChannelHandlerContext();
        Signal signal = source.getSignal();
        String consumerId = signal.getMessage();
        Set<MessageType> messageTypes = signal.getMessageTypes();
        for (MessageType messageType : messageTypes) {
            consumerHolderBuilder.register(consumerId, messageType, ctx);
            consumerReversePool.add(ctx, consumerId, messageType);
        }
        persisConsumerHolder.addConsumer(messageTypes, consumerId);
        eventPublisher.publishEvent(new RedeliveryEvent(new Redelivery(messageTypes, consumerId, ctx)));
        ctx.writeAndFlush(new Signal(Signal.RequestType.ACK_SUCCESS));
    }

    private boolean validate(ConnectionSuccess source){
        ChannelHandlerContext ctx = source.getChannelHandlerContext();
        Signal signal = source.getSignal();
        Set<MessageType> messageTypes = signal.getMessageTypes();
        if(CollectionUtils.isEmpty(messageTypes)) {
            ctx.write(new Signal(Signal.RequestType.ACK_FAIL, "监听的消息类型不能为空，连接建立失败"));
            ctx.close();
            return false;
        }
        return true;
    }
}
