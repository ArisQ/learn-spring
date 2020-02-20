package love.lipbcu.tacocloud.messaging.rabbitmq;

import love.lipbcu.tacocloud.Order;
import love.lipbcu.tacocloud.OrderRepository;
import love.lipbcu.tacocloud.messaging.OrderMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "msg/rabbitmq/sending", produces = "text/plain")
public class RabbitSendingController {

    private final OrderMessagingService orderMessagingService;
    private final OrderRepository orderRepository;

    @Autowired
    public RabbitSendingController(@Qualifier("rabbitmq") OrderMessagingService orderMessagingService, OrderRepository orderRepository) {
        this.orderMessagingService = orderMessagingService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{orderId}")
    public String send(@PathVariable Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent())
            return "order not found";
        Order order = optionalOrder.get();
        orderMessagingService.sendOrder(order);
        return String.format("success %s", order.toString());
    }
}
