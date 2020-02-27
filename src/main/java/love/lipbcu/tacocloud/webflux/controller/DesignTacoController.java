package love.lipbcu.tacocloud.webflux.controller;

import love.lipbcu.tacocloud.webflux.domain.Taco;
import love.lipbcu.tacocloud.webflux.repository.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/reactive/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoController {

    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public Flux<Taco> recentTacos() {
//        return Flux.fromIterable(tacoRepo.findAll(Sort.by("createdAt").descending())).take(12);
        return tacoRepo.findAll().take(12); // JPA不支持ReactiveCrudRepository
    }

    @GetMapping("/{id}")
    public Mono<Taco> tacoById(@PathVariable("id") String id) {
//        return Mono.justOrEmpty(tacoRepo.findById(id));
        return tacoRepo.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
//        return tacoMono.map(taco -> {
//            return tacoRepo.save(taco);
//        });
        // tacoMono.block();
        return tacoRepo.saveAll(tacoMono).next();
    }

    // RxJava
//    @GetMapping("/recent")
//    public Observable<Taco> recentTacos() {
//        return tacoService.getRecentTacos();
//    }
//    @GetMapping("/id")
//    public Single<Taco> tacoById(@PathVariable("id") Long id){
//        return tacoService.lookupTaco(id);
//    }

}
