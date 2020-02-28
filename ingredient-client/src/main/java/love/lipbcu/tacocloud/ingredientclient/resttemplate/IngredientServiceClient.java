package love.lipbcu.tacocloud.ingredientclient.resttemplate;

import love.lipbcu.tacocloud.ingredientclient.Ingredient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class IngredientServiceClient {
    private final RestTemplate rest;

    public IngredientServiceClient(RestTemplate rest) {
        this.rest = rest;
    }

    public Iterable<Ingredient> getAllIngredient() {
        Ingredient[] ingredients = rest.getForObject("http://ingredient-service/ingredients", Ingredient[].class);
        return Arrays.asList(ingredients);
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://ingredient-service/ingredients/{id}", Ingredient.class, ingredientId);
    }
}
