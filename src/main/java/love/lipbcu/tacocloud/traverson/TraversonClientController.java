package love.lipbcu.tacocloud.traverson;

import love.lipbcu.tacocloud.Ingredient;
import love.lipbcu.tacocloud.Taco;
import love.lipbcu.tacocloud.hateoas.TacoResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.Collection;

@Controller
@RequestMapping(path = "/traverson")
public class TraversonClientController {
    private static final Traverson traverson = new Traverson(URI.create("http://localhost:9090/data"), MediaTypes.HAL_JSON);

    @GetMapping(produces = "text/plain")
    public ResponseEntity<String> getTraversonConsuming() {
        StringBuilder sb = new StringBuilder();

        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType = new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
        };
        CollectionModel<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);
        Collection<Ingredient> ingredients = ingredientRes.getContent();
        sb.append(ingredients);
        sb.append("\n\n");

        ParameterizedTypeReference<CollectionModel<Taco>> tacoType = new ParameterizedTypeReference<CollectionModel<Taco>>() {
        };
        CollectionModel<Taco> tacoRes = traverson.follow("tacos").follow("recents").toObject(tacoType);
        // or
//        CollectionModel<Taco> tacoRes = traverson.follow("tacos", "recents").toObject(tacoType);
        Collection<Taco> tacos = tacoRes.getContent();
        sb.append(tacos);
        sb.append("\n\n");

        String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();
        sb.append("ingredients url: \n");
        sb.append(ingredientsUrl);
        sb.append("\n\n");
//        rest.postForObject(ingredientsUrl,ingredient,Ingredient.class); // 结合Traverson和RestTemplate

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }
}
