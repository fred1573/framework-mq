/**
 * 
 */
package com.tomasky.framework.mc.producer;

import com.tomato.mq.support.core.AbstractMessage;
import com.tomato.mq.support.core.MessageDomain;

/**
 * MOM客户端
 * @author Hunhun
 *
 * 下午3:27:24
 */
public interface MQClient {
	/**
	 * 发送消息
	 */
	void send(AbstractMessage message, MessageDomain messageDomain) ;

}
