package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientDTO> findById(String ingredientId);

    Mono<IngredientDTO> saveIngredientOnRecipe(String recipeId, IngredientDTO ingredientDTO);

    Mono<Void> deleteById(String ingredientId);
}
