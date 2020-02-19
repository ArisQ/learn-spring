package love.lipbcu.tacocloud.messaging.jms.sending;

import love.lipbcu.tacocloud.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
