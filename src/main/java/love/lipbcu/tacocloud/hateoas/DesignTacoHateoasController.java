package love.lipbcu.tacocloud.hateoas;

import love.lipbcu.tacocloud.Taco;
import love.lipbcu.tacocloud.TacoRepository;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping(path = "/hateoas/design", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoHateoasController {

    private final TacoRepository tacoRepository;

    public DesignTacoHateoasController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/recent")
    public CollectionModel<TacoResource> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

//        CollectionModel<EntityModel<Taco>> recentTacosModel = CollectionModel.wrap(tacos);
//        recentTacosModel.add(new Link("http://localhost:8080/hateoas/design/recent", "recents"));
//        recentTacosModel.add(WebMvcLinkBuilder.linkTo(DesignTacoHateoasController.class)
//                .slash("recent")
//                .withRel("recents"));

        CollectionModel<TacoResource> recentTacosModel = new TacoResourceAssembler().toCollectionModel(tacos);

        recentTacosModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DesignTacoHateoasController.class).recentTacos()
        ).withRel("recents"));
        return recentTacosModel;
    }
}
