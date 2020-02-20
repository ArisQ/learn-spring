package love.lipbcu.tacocloud.messaging.jms.receiving;

import lombok.Getter;
import love.lipbcu.tacocloud.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JmsOrderListener {
    private final List<Order> orders = new ArrayList<>();

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
