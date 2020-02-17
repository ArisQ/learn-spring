package love.lipbcu.tacocloud.api;

import love.lipbcu.tacocloud.Taco;
import love.lipbcu.tacocloud.TacoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import org.springframework.hateoas.EntityLinks;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoRestController {
    private TacoRepository tacoRepository;

//    @Autowired
//    EntityLinks entityLinks;

    public DesignTacoRestController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        if (optionalTaco.isPresent()) {
            return new ResponseEntity(optionalTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
