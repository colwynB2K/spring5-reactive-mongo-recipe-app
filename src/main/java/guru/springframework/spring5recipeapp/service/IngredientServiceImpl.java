package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.mapper.IngredientMapper;
import guru.springframework.spring5recipeapp.repository.reactive.IngredientReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientReactiveRepository ingredientRepository;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientServiceImpl(IngredientReactiveRepository ingredientRepository, RecipeService recipeService,
                                 UnitOfMeasureService unitOfMeasureService, IngredientMapper ingredientMapper) {
        this.recipeService = recipeService;
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureService = unitOfMeasureService;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public Mono<IngredientDTO> findById(String ingredientId) {
        return ingredientRepository.findById(ingredientId).map(ingredientMapper::toDTO);
    }

    @Override
    public Mono<IngredientDTO> saveIngredientOnRecipe(String recipeId, IngredientDTO ingredientDTO) {
        RecipeDTO recipeDTO = recipeService.findById(recipeId);
        UnitOfMeasureDTO unitOfMeasureDTO = unitOfMeasureService.findById(ingredientDTO.getUnitOfMeasure().getId()).block();  // The incoming uomDTO will only contain an id from the form select box here

        ingredientDTO.setUnitOfMeasure(unitOfMeasureDTO);
        if (ingredientDTO.getId() != null) {
            recipeDTO.getIngredients().remove(ingredientDTO); // As ingredient equality is now determined by their id, this means the incoming ingredientDTO and the existing one are considered equal, so we can just replace it by removing it first
        }
        ingredientDTO.setRecipe(recipeDTO);
        recipeDTO.getIngredients().add(ingredientDTO);          // Associate new ingredient with recipe

        RecipeDTO savedRecipeDTO = recipeService.save(recipeDTO);                               // Now save the updated recipeDTO which originally came from the database back to the database

        // To be able to display the saved ingredient data (for further editing), we need to return the savedIngredient (by fetching it from the saved Recipe again)
        // When we are updating an existing ingredient the incoming ingredientDTO will already contain the new state, so we can just return that
        if (ingredientDTO.getId() != null) {
            return Mono.just(ingredientDTO);
        } else {
            // If we are saving a NEW ingredient the incoming ingredientDTO will NOT have an id, so we can only fetch it from the saved Recipe
            // by looking for an Ingredient with the same Name value.
            // That may not be 100% safe, but let's be honest... if you would have 2 identical ingredients on the same recipe... shit is already hitting the fan
            return Mono.just(savedRecipeDTO.getIngredients()
                    .stream()
                    .filter(recipeIngredient -> recipeIngredient.getName().equals(ingredientDTO.getName()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientDTO.getAmount())) // Just in case someone likes to add multiple ingredient records with same name
                    .filter(recipeIngredient -> recipeIngredient.getUnitOfMeasure().getId().equals(ingredientDTO.getUnitOfMeasure().getId())) // Filtering UOM (object) doesn't work, you need to filter on a property
                    .findFirst().get());
        }
    }

    @Override
    public Mono<Void> deleteById(String ingredientId) {
       return ingredientRepository.deleteById(ingredientId);
    }

}
