package com.tomasky.framework.mc.support.event.model;

import com.tomasky.framework.mc.netty.model.MsgSend;
import org.springframework.context.ApplicationEvent;

/**
 * @author Hunhun
 *         2015-12-04 10:07
 */
public class MsgSendEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MsgSendEvent(MsgSend source) {
        super(source);
    }
}
