package com.tomasky.framework.mc.support.event.model;

import org.springframework.context.ApplicationEvent;

/**
 * @author Hunhun
 *         2015-12-03 10:07
 */
public class RedeliveryEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public RedeliveryEvent(Object source) {
        super(source);
    }
}
