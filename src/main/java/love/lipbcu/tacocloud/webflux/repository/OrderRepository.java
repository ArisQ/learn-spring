package love.lipbcu.tacocloud.webflux.repository;

import love.lipbcu.tacocloud.webflux.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {
}
