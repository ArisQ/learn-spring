package love.lipbcu.tacocloud.api;

import love.lipbcu.tacocloud.Ingredient;
import love.lipbcu.tacocloud.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/ingredients",produces = "application/json")
public class IngredientRestController {

    private final IngredientRepository ingredientRepository;

    public IngredientRestController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping(consumes = "application/json")
    public Ingredient createIngredient(@RequestBody Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }
    
    @GetMapping
    public Iterable<Ingredient> getIngredientList(){
        return ingredientRepository.findAll();
    }

    @GetMapping(path = "/{ingredientId}")
    public ResponseEntity< Ingredient> getIngredientDetail(@PathVariable("ingredientId") String ingredientId){
        Optional<Ingredient> ingredient= ingredientRepository.findById(ingredientId);
        if(ingredient.isPresent())
            return new ResponseEntity<>(ingredient.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/{ingredientId}",consumes = "application/json")
    public Ingredient updateIngredient(@PathVariable("ingredientId") String ingredientId,
                                       @RequestBody Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }
}
