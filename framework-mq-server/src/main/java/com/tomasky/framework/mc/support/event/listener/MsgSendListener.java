package com.tomasky.framework.mc.support.event.listener;

import com.tomasky.framework.mc.netty.model.MsgSend;
import com.tomasky.framework.mc.support.event.model.MsgSendEvent;
import com.tomasky.framework.mc.support.event.processor.MsgSendProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Hunhun
 * 2015-12-04 10:15
 */
@Component
public class MsgSendListener implements ApplicationListener<MsgSendEvent> {

    @Autowired
    private MsgSendProcessor msgSendProcessor;

    @Override
    public void onApplicationEvent(MsgSendEvent event) {
        msgSendProcessor.sendToMOM((MsgSend) event.getSource());
    }

}
