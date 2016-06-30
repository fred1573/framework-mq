/**
 * 
 */
package com.tomasky.framework.mc.support.jms;

import com.tomato.mq.support.core.MessageDomain;
import com.tomato.mq.support.message.MessageType;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Destination;

/**
 * @author Hunhun
 *
 * 下午2:10:17
 */

public class DestinationBuilder {

	public static Destination getDestination(MessageType messageType, MessageDomain messageDomain) {
		if(MessageDomain.TOPIC.equals(messageDomain)){
			return new ActiveMQTopic(com.tomato.mq.support.core.Destination.DEST_MAP.get(messageType));
		}else if(MessageDomain.QUEUE.equals(messageDomain)){
			return new ActiveMQQueue(com.tomato.mq.support.core.Destination.DEST_MAP.get(messageType));
		}else{
			throw new RuntimeException("消息模型未识别，messageDomain=" + messageDomain);
		}
	}
}
