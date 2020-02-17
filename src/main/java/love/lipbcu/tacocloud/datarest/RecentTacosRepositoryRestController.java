package love.lipbcu.tacocloud.datarest;

import love.lipbcu.tacocloud.Taco;
import love.lipbcu.tacocloud.TacoRepository;
import love.lipbcu.tacocloud.hateoas.TacoResource;
import love.lipbcu.tacocloud.hateoas.TacoResourceAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.PersistentEntityResourceProcessor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class RecentTacosRepositoryRestController {
    private TacoRepository tacoRepository;

    public RecentTacosRepositoryRestController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();
        CollectionModel<TacoResource> recentResources = new TacoResourceAssembler().toCollectionModel(tacos);
        recentResources.add(linkTo(methodOn(RecentTacosRepositoryRestController.class).recentTacos()).withRel("recents"));
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }

    @Bean
    public RepresentationModelProcessor<CollectionModel<EntityModel<Taco>>> tacoProcessor(EntityLinks links) {
        return new RepresentationModelProcessor<CollectionModel<EntityModel<Taco>>>() {
            @Override
            public CollectionModel<EntityModel<Taco>> process(CollectionModel<EntityModel<Taco>> model) {
                model.add(links.linkFor(Taco.class).slash("recent").withRel("recents"));
                return model;
            }
        };
    }

}
