package com.tomasky.framework.mc.support.consumer;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author frd
 *         2016/6/28.
 */
public abstract class AbstractConsumer {

    private String consumerId;
    private List<ChannelHandlerContext> ctxs = Collections.synchronizedList(new ArrayList<>());

    public AbstractConsumer(String consumerId, ChannelHandlerContext ctx) {
        this.consumerId = consumerId;
        register(ctx);
    }

    public void register(ChannelHandlerContext ctx) {
        ctxs.add(ctx);
    }

    public String getConsumerId() {
        return consumerId;
    }

    public List<ChannelHandlerContext> getCtxs() {
        return ctxs;
    }

    /**
     * 随机选取一个消费者发送
     * @return
     */
    public ChannelHandlerContext vote() {
        int size = ctxs.size();
        if(size <= 0) {
            return null;
        }
        return ctxs.get(new Random().nextInt(size));
    }
}
