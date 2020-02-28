package love.lipbcu.tacocloud.ingredientclient.resttemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/ingredients", produces = "text/plain")
public class RestIngredientController {
    private final IngredientServiceClient client;

    public RestIngredientController(IngredientServiceClient client) {
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
