package love.lipbcu.tacocloud.ingredientclient.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/feign/ingredients", produces = "text/plain")
public class FeignIngredientController {
    private final IngredientClient client;

    public FeignIngredientController(IngredientClient client) {
        this.client = client;
    }


    @GetMapping
    public String getAllIngredient() {
        return client.getAllIngredient().toString();
    }

    @GetMapping(path = "/{id}")
    public String getIngredientById(@PathVariable("id") String ingredientId) {
        return client.getIngredientById(ingredientId).toString();
    }
}
