package love.lipbcu.tacocloud.messaging.jms.sending;

import love.lipbcu.tacocloud.Order;
import love.lipbcu.tacocloud.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/msg/jms/sending", produces = "text/plain")
public class JmsSendingController {

    private final JmsOrderMessagingService jmsOrderMessagingService;
    private final OrderRepository orderRepository;

    @Autowired
    public JmsSendingController(JmsOrderMessagingService jmsOrderMessagingService, OrderRepository orderRepository) {
        this.jmsOrderMessagingService = jmsOrderMessagingService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{orderId}")
    public String send(@PathVariable(value = "orderId") Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent())
            return "order not found";
        Order order = optionalOrder.get();
        jmsOrderMessagingService.sendOrder(order);
        return String.format("success %s", order.toString());
    }
}
