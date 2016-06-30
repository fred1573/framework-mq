/**
 * 
 */
package com.tomasky.framework.mc.producer;

import com.tomasky.framework.mc.support.jms.DestinationBuilder;
import com.tomato.mq.support.core.AbstractMessage;
import com.tomato.mq.support.core.MessageDomain;
import com.tomato.mq.support.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * MOM客户端
 * @author Hunhun
 *
 * 下午3:27:24
 */
public class AMQClient  implements MQClient{

	private static final Logger LOGGER = LoggerFactory.getLogger(AMQClient.class);

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public void send(AbstractMessage message, MessageDomain messageDomain) {
		MessageType messageType = message.getMessageType();
		jmsTemplate.send(DestinationBuilder.getDestination(messageType, messageDomain), session -> {
			return session.createObjectMessage(message);
		});
		LOGGER.debug("----------消息已发送到AMQ------------");
	}

}
