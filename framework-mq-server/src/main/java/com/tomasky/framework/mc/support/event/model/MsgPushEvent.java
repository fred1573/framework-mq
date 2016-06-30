package com.tomasky.framework.mc.support.event.model;

import com.tomato.mq.support.core.AbstractMessage;
import org.springframework.context.ApplicationEvent;

/**
 * @author Hunhun
 *         2015-11-30 15:29
 */
public class MsgPushEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MsgPushEvent(AbstractMessage source) {
        super(source);
    }
}
