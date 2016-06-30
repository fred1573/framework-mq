/**
 * 
 */
package com.tomato.mq.client.produce;

import com.tomato.mq.support.core.AbstractMessage;

/**
 * MOM客户端
 * @author Hunhun
 *
 * 下午3:27:24
 */
public interface MQProducer {

	/**
	 * 发送消息，失败抛出异常
	 * 文本消息须为json格式，且包含system、type 和 content三个字段
	 * @param message
	 */
	void send(AbstractMessage message);
}
