package love.lipbcu.tacocloud.messaging.kafka.receiving;

import love.lipbcu.tacocloud.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/msg/kafka/receiving", produces = "text/plain")
public class KafkaReceivingController {
    private final KafkaOrderListener orderListener;

    @Autowired
    public KafkaReceivingController(KafkaOrderListener orderListener) {
        this.orderListener = orderListener;
    }

    @GetMapping(path = "/listener-result")
    public String listenerResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n\n");
        List<Order> orders = orderListener.getOrders();
        for (Order order : orders) {
            sb.append(order.toString());
            sb.append("\n\n");
        }
        sb.append("]\n\n");
        return sb.toString();
//        return orderListener.getOrders().toString();
    }
}
