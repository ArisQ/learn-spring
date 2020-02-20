package love.lipbcu.tacocloud.messaging;

import love.lipbcu.tacocloud.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
