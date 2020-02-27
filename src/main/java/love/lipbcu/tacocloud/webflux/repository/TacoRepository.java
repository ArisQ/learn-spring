package love.lipbcu.tacocloud.webflux.repository;

import love.lipbcu.tacocloud.webflux.domain.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TacoRepository extends ReactiveCrudRepository<Taco, String> {
    Flux<Taco> findByOrderByCreatedAtDesc();
}
