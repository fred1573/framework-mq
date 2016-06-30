package com.tomato.mq.client.event.publisher;

import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.netty.consumer.MsgNettyClient;
import com.tomato.mq.client.support.ThreadPoolExecutorBuilder;
import com.tomato.mq.client.support.ThreadPoolExecutorModel;
import com.tomato.mq.support.message.MessageType;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Hunhun
 *         2015-09-15 19:32
 */
public class MsgEventPublisher {

    private static MsgEventPublisher instance;

    private ThreadPoolExecutorModel threadPoolExecutorModel;

    private Map<MessageType, MsgEventListener> listenerMaps = new HashMap<>();

    private MsgEventPublisher(ThreadPoolExecutorModel threadPoolExecutorModel) {
        this.threadPoolExecutorModel = threadPoolExecutorModel;
    }

    public static MsgEventPublisher getInstance() {
        return wrapPublisher(new ThreadPoolExecutorModel());
    }

    public static MsgEventPublisher getInstance(int corePoolSize, int maxPoolSize) {
        return wrapPublisher(new ThreadPoolExecutorModel(corePoolSize, maxPoolSize));
    }

    public static MsgEventPublisher getInstance(ThreadPoolExecutorModel threadPoolExecutorModel) {
        return wrapPublisher(threadPoolExecutorModel);
    }

    private static MsgEventPublisher wrapPublisher(ThreadPoolExecutorModel threadPoolExecutorModel) {
        if (instance == null) {
            synchronized (MsgEventPublisher.class) {
                if (instance == null) {
                    instance = new MsgEventPublisher(threadPoolExecutorModel);
                }
            }
        }
        return instance;
    }

    /**
     * unique consumerId
     *
     * @param listener
     * @param consumerId
     */
    public void addListener(MsgEventListener listener, MessageType messageType, String consumerId) {
        checkNull(listener, messageType, consumerId);
        MsgEventListener msgEventListener = listenerMaps.get(messageType);
        if (msgEventListener != null) {
            throw new RuntimeException("同一消息类型的监听只能添加一个");
        }
        Set<MessageType> messageTypes = new HashSet<>();
        messageTypes.add(messageType);
        MsgNettyClient.getInstance(consumerId, messageTypes).getSocketChannel();
        listenerMaps.put(messageType, listener);
    }

    public void addListener(MsgEventListener listener, String consumerId, MessageType ... messageTypes) {
        checkNull(listener, consumerId, messageTypes);
        Set<MessageType> messageTypeSet = new HashSet<>();
        for (MessageType messageType : messageTypes) {
            MsgEventListener msgEventListener = listenerMaps.get(messageType);
            if (msgEventListener != null) {
                throw new RuntimeException("同一消息类型的监听只能添加一个");
            }
            messageTypeSet.add(messageType);
            listenerMaps.put(messageType, listener);
        }
        MsgNettyClient.getInstance(consumerId, messageTypeSet).getSocketChannel();
    }

    public void publish(final MsgEvent msgEvent, final MessageType messageType) {
        ThreadPoolExecutorBuilder.getThreadPoolExecutor(threadPoolExecutorModel).execute(new Runnable() {
            @Override
            public void run() {
                MsgEventListener msgEventListener = listenerMaps.get(messageType);
                if (msgEventListener == null) {
                    return;
                }
                msgEvent.setMessageType(messageType);
                msgEventListener.onEvent(msgEvent);
            }
        });
    }

    private void checkNull(MsgEventListener listener, String consumerId, MessageType[] messageTypes){
        if(listener == null || messageTypes.length == 0 || StringUtils.isBlank(consumerId)){
            throw new RuntimeException("监听、消息类型及消费者标识缺一不可");
        }
    }

    private void checkNull(MsgEventListener listener, MessageType messageType, String consumerId){
        if(messageType == null){
            throw new RuntimeException("消息类型不能为空");
        }
        MessageType[] messageTypes = new MessageType[]{messageType};
        checkNull(listener, consumerId, messageTypes);
    }

}
