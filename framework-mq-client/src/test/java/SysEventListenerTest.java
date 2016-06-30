import com.tomato.mq.client.event.listener.MsgEventListener;
import com.tomato.mq.client.event.model.MsgEvent;
import com.tomato.mq.client.event.publisher.MsgEventPublisher;
import com.tomato.mq.support.message.MessageType;

/**
 * @author Hunhun
 *         17:49
 */
public class SysEventListenerTest implements MsgEventListener{

    public static void main(String[] args) {
        new SysEventListenerTest();
    }

    public SysEventListenerTest() {
        MsgEventPublisher.getInstance().addListener(this, "MQ_CLIENT_LISTENER", MessageType.SYS_EVENT);
    }

    @Override
    public void onEvent(MsgEvent event) {
        System.out.println(event.getMessageType() + "\t" + event.getSource());
    }
}
