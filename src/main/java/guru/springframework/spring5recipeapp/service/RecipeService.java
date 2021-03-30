package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<RecipeDTO> findAll();

    Mono<RecipeDTO> findById(String id);

    Mono<RecipeDTO> save(RecipeDTO recipeDTO);

    Mono<Void> deleteById(String id);
}
