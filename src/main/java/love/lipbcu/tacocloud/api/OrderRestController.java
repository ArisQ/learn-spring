package love.lipbcu.tacocloud.api;

import love.lipbcu.tacocloud.Order;
import love.lipbcu.tacocloud.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderRestController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping
    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Order postOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping(value = "/{orderId}", consumes = "application/json")
    public Order putOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId,
                            @RequestBody Order patch) {
        Order order = orderRepository.findById(orderId).get();
        if (patch.getName() != null)
            order.setName(patch.getName());
        if (patch.getStreet() != null)
            order.setStreet(patch.getStreet());
        if (patch.getCity() != null)
            order.setCity(patch.getCity());
        if (patch.getState() != null)
            order.setState(patch.getState());
        if (patch.getZip() != null)
            order.setZip(patch.getZip());
        if (patch.getCcNumber() != null)
            order.setCcNumber(patch.getCcNumber());
        if (patch.getCcExpiration() != null)
            order.setCcExpiration(patch.getCcExpiration());
        if (patch.getCcCVV() != null)
            order.setCcCVV(patch.getCcCVV());
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
