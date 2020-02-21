package love.lipbcu.tacocloud.messaging.kafka.receiving;

import love.lipbcu.tacocloud.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaOrderListener {
    private List<Order> orders = new ArrayList<>();

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void receiveOrder(Order order) {
        System.out.println("got new order");
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
