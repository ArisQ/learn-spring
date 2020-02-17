package love.lipbcu.tacocloud.hateoas;

import love.lipbcu.tacocloud.Ingredient;
import love.lipbcu.tacocloud.IngredientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/hateoas/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientHateoasController {

    private final IngredientRepository ingredientRepository;

    public IngredientHateoasController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public CollectionModel<IngredientResource> ingredientList() {
        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();
        return new IngredientResourceAssembler().toCollectionModel(ingredients);
    }

    @GetMapping(path = "{ingredientId}")
    public ResponseEntity<IngredientResource> ingredientDetail(@PathVariable("ingredientId") String ingredientId) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        return ingredient.map(
                value -> new ResponseEntity<>(new IngredientResourceAssembler().toModel(value), HttpStatus.CREATED)
        ).orElseGet(
                () -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND)
        );
    }
}
