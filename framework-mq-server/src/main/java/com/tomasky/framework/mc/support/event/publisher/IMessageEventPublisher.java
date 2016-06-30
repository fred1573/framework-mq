package com.tomasky.framework.mc.support.event.publisher;

import org.springframework.context.ApplicationEvent;

/**
 * @author Simon
 */
public interface IMessageEventPublisher {
    void publishEvent(ApplicationEvent event);
}
