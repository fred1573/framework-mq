/**
 * 
 */
package com.tomato.mq.client.support;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Hunhun
 *
 * 下午5:08:51
 */
public class SystemConfig {

	public static final String MQ_SERVER_HOST = "mq.server.host";
	public static final String MQ_SERVER_PORT = "mq.server.port";

	public static final Map<String, String> PROPERTIES = new HashMap<>();

	static {
		Properties p = new Properties();
		String profile = System.getProperty("mq.profiles.active");
		profile = StringUtils.isBlank(profile) ? "/production" : "/" + profile;
		try {
			p.load(SystemConfig.class.getResourceAsStream(profile + "/socket.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PROPERTIES.put(MQ_SERVER_HOST, p.get(MQ_SERVER_HOST).toString());
		PROPERTIES.put(MQ_SERVER_PORT, p.get(MQ_SERVER_PORT).toString());
	}
	
}
