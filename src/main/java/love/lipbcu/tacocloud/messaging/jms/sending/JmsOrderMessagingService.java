package love.lipbcu.tacocloud.messaging.jms.sending;

import love.lipbcu.tacocloud.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {
    private JmsTemplate jms;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }


    @Override
    public void sendOrder(Order order) {
//        jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
        jms.convertAndSend("tacocloud.order.queue", order,this::addOrderSource);
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }

}
