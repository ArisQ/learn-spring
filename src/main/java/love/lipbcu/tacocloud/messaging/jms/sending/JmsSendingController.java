package love.lipbcu.tacocloud.messaging.jms.sending;

import love.lipbcu.tacocloud.Order;
import love.lipbcu.tacocloud.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/msg/jms/sending", produces = "text/plain")
public class JmsSendingController {

    private final OrderMessagingService orderMessagingService;
    private final OrderRepository orderRepository;

    @Autowired
    public JmsSendingController(OrderMessagingService orderMessagingService, OrderRepository orderRepository) {
        this.orderMessagingService = orderMessagingService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{orderId}")
    public String send(@PathVariable(value = "orderId") Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent())
            return "order not found";
        Order order = optionalOrder.get();
        orderMessagingService.sendOrder(order);
        return String.format("success %s", order.toString());
    }
}
