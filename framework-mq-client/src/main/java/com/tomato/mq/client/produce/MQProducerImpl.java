package com.tomato.mq.client.produce;

import com.alibaba.fastjson.JSON;
import com.tomato.mq.client.netty.producer.ProducerNettyClient;
import com.tomato.mq.support.core.AbstractMessage;
import com.tomato.mq.support.netty.model.Signal;
import io.netty.channel.Channel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Hunhun
 *         13:02
 */
public class MQProducerImpl implements MQProducer {
    private static final String FAIL_MSG_FILE = "FAILED_MSG";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(MQProducerImpl.class);

    public void send(final AbstractMessage message) {
        new Thread(new Runnable() {
            public void run() {
                ProducerNettyClient producerNettyClient = new ProducerNettyClient();
                try {
                    Channel channel = producerNettyClient.getSocketChannel();
                    if (channel == null) {
                        LOGGER.error("连接服务器失败");
                        throw new InterruptedException();
                    }
                    channel.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, JSON.toJSONString(message)));
                    try {
                        File file = new File(FAIL_MSG_FILE);
                        if (file.exists()) {
                            List datas = FileUtils.readLines(file, DEFAULT_ENCODING);
                            Iterator localIterator;
                            if ((datas != null) && (datas.size() > 0)) {
                                for (localIterator = datas.iterator(); localIterator.hasNext(); ) {
                                    Object data = localIterator.next();
                                    channel.writeAndFlush(new Signal(Signal.RequestType.TRANSPORT, data.toString()));
                                }
                            }
                            file.delete();
                        }
                    } catch (IOException e) {
                        LOGGER.error("重发失败消息时异常", e);
                    }
                } catch (ConnectException e) {
                    LOGGER.error("连接服务器失败,", e);
                    List data = new ArrayList();
                    data.add(JSON.toJSONString(message));
                    try {
                        File newFile = new File(FAIL_MSG_FILE);
                        if (!newFile.exists()) {
                            newFile.createNewFile();
                        }
                        OutputStream os = new FileOutputStream(newFile, true);
                        IOUtils.writeLines(data, null, os, DEFAULT_ENCODING);
                        os.flush();
                    } catch (IOException e1) {
                        LOGGER.error("记录发送失败的消息时异常", e);
                    }
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    producerNettyClient.releaseConnections();
                }
            }
        }).start();
    }

}