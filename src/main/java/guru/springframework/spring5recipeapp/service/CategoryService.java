package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.CategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Mono<CategoryDTO> getCategoryByName(String name);

    Flux<CategoryDTO> findAll();
}
