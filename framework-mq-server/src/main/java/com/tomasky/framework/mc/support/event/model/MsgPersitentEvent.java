package com.tomasky.framework.mc.support.event.model;

import com.tomato.mq.support.core.AbstractMessage;
import org.springframework.context.ApplicationEvent;

/**
 * @author Hunhun
 *         2015/12/4
 */
public class MsgPersitentEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public MsgPersitentEvent(AbstractMessage source) {
        super(source);
    }
}
