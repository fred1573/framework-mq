package com.tomasky.framework.mc.support.event.processor;

import com.tomasky.framework.mc.netty.model.MsgSend;

/**
 * @author Hunhun
 *         2015/12/4
 */
public interface MsgSendProcessor {

    /**
     * 发送消息到消息中间件
     * @param msgSend
     */
    void sendToMOM(MsgSend msgSend);
}
