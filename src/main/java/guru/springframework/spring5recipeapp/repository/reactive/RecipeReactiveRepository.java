package guru.springframework.spring5recipeapp.repository.reactive;

import guru.springframework.spring5recipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
