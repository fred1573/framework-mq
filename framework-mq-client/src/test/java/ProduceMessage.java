import com.alibaba.fastjson.JSONObject;
import com.tomato.mq.client.produce.MQProducer;
import com.tomato.mq.client.support.MQClientBuilder;
import com.tomato.mq.client.support.ThreadPoolExecutorBuilder;
import com.tomato.mq.client.support.ThreadPoolExecutorModel;
import com.tomato.mq.support.core.SysMessage;
import com.tomato.mq.support.core.TextMessage;
import com.tomato.mq.support.message.MessageType;

/**
 * @author Hunhun
 *         2015-09-11 15:04
 */
public class ProduceMessage {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutorModel threadModel = new ThreadPoolExecutorModel();
        for (int i = 0; i < 1; i++) {
            ThreadPoolExecutorBuilder.getThreadPoolExecutor(threadModel).execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1; j++) {
                        long start = System.currentTimeMillis();
                        new ProduceMessage().sendBizMsg();
                        System.out.println(System.currentTimeMillis() - start);
                    }
                }
            });
        }
    }

    private void sendSysMsg() {
        MQProducer mqClient = MQClientBuilder.build();

        String content = CONTENT;
        SysMessage sysMessage = new SysMessage("oms", "SPECIAL_DATE_CLOSE_INN", content);
        mqClient.send(sysMessage);
    }

    private void sendBizMsg() {
        MQProducer mqClient = MQClientBuilder.build();
        JSONObject json = new JSONObject();
        json.put("b", 2);
        for (int i = 0; i < 1; i++) {
            mqClient.send(new TextMessage(json.toJSONString(), MessageType.BIZ_MESSAGE, "mq_client"));
        }
    }


    private static final String CONTENT = "1";
}


