package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;

public interface IngredientService {
    IngredientDTO findById(String ingredientId);

    IngredientDTO saveIngredientOnRecipe(String recipeId, IngredientDTO ingredientDTO);

    void deleteById(String ingredientId);
}
