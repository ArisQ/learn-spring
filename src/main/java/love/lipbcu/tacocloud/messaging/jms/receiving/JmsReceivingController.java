package love.lipbcu.tacocloud.messaging.jms.receiving;

import love.lipbcu.tacocloud.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.util.List;

@RestController
@RequestMapping(path = "/msg/jms/receiving", produces = "text/plain")
public class JmsReceivingController {

    private final JmsOrderReceiver orderReceiver;
    private final JmsOrderListener orderListener;

    public JmsReceivingController(JmsOrderReceiver orderReceiver, JmsOrderListener orderListener) {
        this.orderReceiver = orderReceiver;
        this.orderListener = orderListener;
    }

    @GetMapping(path = "/pull")
    public String pull() throws JMSException {
        Order order = orderReceiver.receiveOrder();
        return order.toString();
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
