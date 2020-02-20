package love.lipbcu.tacocloud.messaging.rabbitmq;

import love.lipbcu.tacocloud.Order;
import love.lipbcu.tacocloud.messaging.OrderMessagingService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("rabbitmq")
public class RabbitOrderMessagingService implements OrderMessagingService {

    private final RabbitTemplate rabbit;

    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    public void sendOrder(Order order) {
        /*
        MessageConverter converter = rabbit.getMessageConverter();
        MessageProperties props = new MessageProperties();
        Message message = converter.toMessage(order, props);
        rabbit.send("tacocloud.order", message);
         */
        rabbit.convertAndSend("tacocloud.order", order, this::addOrderSource);
    }

    private Message addOrderSource(Message message) throws AmqpException {
        MessageProperties props = message.getMessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
