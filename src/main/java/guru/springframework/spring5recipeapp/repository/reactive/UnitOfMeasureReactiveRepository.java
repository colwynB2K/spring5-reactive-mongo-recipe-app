package guru.springframework.spring5recipeapp.repository.reactive;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {
    Mono<UnitOfMeasure> findByName(String name);

    Mono<UnitOfMeasure> findByUnit(String unit);
}
