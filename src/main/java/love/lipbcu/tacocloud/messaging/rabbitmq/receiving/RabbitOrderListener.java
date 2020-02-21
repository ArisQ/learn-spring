package love.lipbcu.tacocloud.messaging.rabbitmq.receiving;

import love.lipbcu.tacocloud.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RabbitOrderListener {
    private List<Order> orders = new ArrayList<>();

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        System.out.println("got new order");
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}



