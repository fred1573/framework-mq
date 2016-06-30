import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.message.MessageType;

/**
 * @author Hunhun
 *         17:49
 */
public class BizMsgListenerTest implements MsgEventListener{

    public static void main(String[] args) {
        new BizMsgListenerTest();
    }

    public BizMsgListenerTest() {
        MsgEventPublisher.getInstance().addListener(this, MessageType.BIZ_MESSAGE, "biz_msg_test");
    }

    @Override
    public void onEvent(MsgEvent event) {
        System.out.println(event.getSource());
    }
}
