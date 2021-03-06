package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.mapper.IngredientMapper;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.mapper.UnitOfMeasureMapper;
import guru.springframework.spring5recipeapp.repository.reactive.IngredientReactiveRepository;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import guru.springframework.spring5recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class IngredientRepositoryServiceImpl implements IngredientService {

    private final IngredientReactiveRepository ingredientReactiveRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientRepositoryServiceImpl(IngredientReactiveRepository ingredientReactiveRepository,
                                           RecipeReactiveRepository recipeReactiveRepository,
                                           UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
                                           IngredientMapper ingredientMapper) {
        this.ingredientReactiveRepository = ingredientReactiveRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;

        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public Mono<IngredientDTO> findById(String recipeId, String ingredientId) {

        return recipeReactiveRepository.findById(recipeId)
                                        .map(recipe -> recipe.getIngredients()
                                                            .stream()
                                                            .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                                                            .findFirst())
                                        .filter(Optional::isPresent)
                                        .map(ingredient -> {
                                                    IngredientDTO ingredientDTO = ingredientMapper.toDTO(ingredient.get()); //
                                                    ingredientDTO.setRecipeId(recipeId);

                                                   return ingredientDTO;
                                        });

        // Likely this code ends up in a null ingredientDTO in the weblayer
        // because of the ingredientMapper not being able to handle the mapping of an Mono<Ingredient> to a Mono<IngredientDTO>???
/*        return ingredientReactiveRepository.findById(ingredientId)
                .map(ingredient -> {
                    IngredientDTO ingredientDTO = ingredientMapper.toDTO(ingredient);
                    ingredientDTO.setRecipeId(recipeId);

                    log.error("ingredientDTO = " + ingredientDTO);

                    return ingredientDTO;
                });*/
    }

    @Override
    public Mono<IngredientDTO> saveIngredientOnRecipe(String recipeId, IngredientDTO ingredientDTO) {
        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
        final Ingredient ingredient = ingredientMapper.toEntityIgnoreRecipeChildObjects(ingredientDTO);
        ingredient.setUnitOfMeasure(unitOfMeasureReactiveRepository.findById(ingredientDTO.getUnitOfMeasure().getId()).block()); // The incoming uomDTO (on ingredientDTO) will only contain an id from the form select box here

        if (ingredient.getId() != null) {
            recipe.getIngredients().remove(ingredient); // As ingredient equality is now determined by their id, this means the incoming ingredientDTO and the existing one are considered equal, so we can just replace it by removing it first
        }
        recipe.addIngredient(ingredient); // Associate new ingredient with recipe
        Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();  // Now save the updated recipe which originally came from the database back to the database

        // To be able to display the saved ingredient data (for further editing), we need to return the savedIngredient (by fetching it from the saved Recipe again)
        // When we are updating an existing ingredient the incoming ingredientDTO will already contain the new state, so we can just return that
        if (ingredient.getId() != null) {
            ingredientDTO = ingredientMapper.toDTO(ingredient);
            ingredientDTO.setRecipeId(recipeId);

            return Mono.just(ingredientDTO);
        } else {
            // If we are saving a NEW ingredient the incoming ingredientDTO will NOT have an id, so we can only fetch it from the saved Recipe
            // by looking for an Ingredient with the same Name value.
            // That may not be 100% safe, but let's be honest... if you would have 2 identical ingredients on the same recipe... shit is already hitting the fan
            Ingredient savedIngredient = savedRecipe.getIngredients()
                    .stream()
                    .filter(recipeIngredient -> recipeIngredient.getName().equals(ingredient.getName()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredient.getAmount())) // Just in case someone likes to add multiple ingredient records with same name
                    .filter(recipeIngredient -> recipeIngredient.getUnitOfMeasure().getId().equals(ingredient.getUnitOfMeasure().getId())) // Filtering UOM (object) doesn't work, you need to filter on a property
                    .findFirst().get();

            ingredientDTO = ingredientMapper.toDTO(savedIngredient);
            ingredientDTO.setRecipeId(recipeId);

            return Mono.just(ingredientDTO);
        }
}

    @Override
    public Mono<Void> deleteById(String ingredientId) {
       return ingredientReactiveRepository.deleteById(ingredientId);
    }

}
