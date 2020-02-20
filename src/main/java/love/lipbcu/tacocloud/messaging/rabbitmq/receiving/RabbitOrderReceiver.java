package love.lipbcu.tacocloud.messaging.rabbitmq.receiving;

import love.lipbcu.tacocloud.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class RabbitOrderReceiver {
    private final RabbitTemplate rabbit;
    private final MessageConverter converter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }

    public Order receiveOrder() {
        Message message = rabbit.receive("tacocloud.order.queue");
        return message != null ? (Order) converter.fromMessage(message) : null;
//        return rabbit.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<Order>() { });
    }
}
