
package com.tomasky.framework.mc.support.event.publisher;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author Simon
 */
@Component
public class MessageEventPublisher implements ApplicationEventPublisherAware, IMessageEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEventPublisher.class);

    private ApplicationEventPublisher publisher;

    @Override
    public void publishEvent(ApplicationEvent event) {
        LOGGER.debug(String.format("【发布事件，内容:%s】", JSON.toJSONString(event.getSource())));
        publisher.publishEvent(event);
        LOGGER.debug("【发布事件------完成】");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
