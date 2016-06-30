package com.tomasky.framework.mc.support.event.model;

import com.tomasky.framework.mc.netty.model.ConnectionSuccess;
import org.springframework.context.ApplicationEvent;

/**
 * @author Hunhun
 *         2015-12-04 10:02
 */
public class ConnectionSucEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public ConnectionSucEvent(ConnectionSuccess source) {
        super(source);
    }
}
