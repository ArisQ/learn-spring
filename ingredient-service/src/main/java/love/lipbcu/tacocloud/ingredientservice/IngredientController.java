package love.lipbcu.tacocloud.ingredientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
public class IngredientController {
    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping
    public Iterable<Ingredient> ingredientList() {
        return ingredientRepo.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Ingredient> ingredientDetail(@PathVariable("id") String ingredientId) {
        Optional<Ingredient> ingredient = ingredientRepo.findById(ingredientId);
        return ingredient
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
