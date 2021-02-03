package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureService {

    Mono<UnitOfMeasureDTO> getUOMByName(String name);

    Flux<UnitOfMeasureDTO> findAll();

    Mono<UnitOfMeasureDTO> findById(String id);
}
