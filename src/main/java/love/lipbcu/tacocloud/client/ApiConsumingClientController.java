package love.lipbcu.tacocloud.client;

import love.lipbcu.tacocloud.Ingredient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/client")
public class ApiConsumingClientController {
    private static final Logger logger=Logger.getLogger("love.lipbcu.tacocloud");

    private static RestTemplate rest=new RestTemplate();
    private static final String baseUrl="http://localhost:9090/api";

    @GetMapping(produces = "text/plain")
    public ResponseEntity<String> getClientConsuming(){
        StringBuilder sb=new StringBuilder("Hello\n");

        Ingredient carn=getIngredientById("CARN");
        System.out.println(carn);
        sb.append(carn);
        sb.append("\n");

        Ingredient newCarn= new Ingredient(carn.getId(),"new name",carn.getType());
        System.out.println(newCarn);
        updateIngredient(newCarn);

        Ingredient carnUpdateResult=getIngredientById("CARN");
        System.out.println(carnUpdateResult);
        sb.append("=== update result: \n");
        sb.append(carnUpdateResult);
        sb.append("\n");

        updateIngredient(carn); // 还原
        sb.append("restored\n");


        logger.info(carn.toString());
        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }

    public Ingredient getIngredientById(String ingredientId){
        // getForObject
        // 1
//        return rest.getForObject(baseUrl+"/ingredients/{id}",Ingredient.class,ingredientId);
        // 2
//        Map<String,String> urlVariables=new HashMap<>();
//        urlVariables.put("id",ingredientId);
//        return rest.getForObject(baseUrl+"/ingredients/{id}",Ingredient.class,urlVariables);
        // 3
//        Map<String,String> urlVariables=new HashMap<>();
//        urlVariables.put("id",ingredientId);
//        URI url= UriComponentsBuilder.fromHttpUrl(baseUrl+"/ingredients/{id}").build(urlVariables);
//        return rest.getForObject(url,Ingredient.class);

        // getForEntity
        ResponseEntity<Ingredient> responseEntity=rest.getForEntity(baseUrl+"/ingredients/{id}",Ingredient.class,ingredientId);
        logger.info("Fetched time:"+ responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public void updateIngredient(Ingredient ingredient){
        rest.put(baseUrl+"/ingredients/{id}",ingredient,ingredient.getId());
    }
}
