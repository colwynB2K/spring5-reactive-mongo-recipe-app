package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.mapper.IngredientMapper;
import guru.springframework.spring5recipeapp.repository.reactive.IngredientReactiveRepository;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import guru.springframework.spring5recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientReactiveRepository mockIngredientReactiveRepository;

    @Mock
    private RecipeReactiveRepository mockRecipeReactiveRepository;

    @Mock
    private UnitOfMeasureReactiveRepository mockUnitOfMeasureReactiveRepository;

    @Mock
    IngredientMapper mockIngredientMapper;

    @InjectMocks
    private IngredientServiceImpl ingredientServiceImpl;

    private Ingredient ingredient;
    private IngredientDTO ingredientDTO;
    private RecipeDTO recipeDTO;

    private final String recipeId = "1";
    private final String ingredientId = "2";
    private final String uomId = "3";

    @BeforeEach
    void setUp() {
        recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);

        ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredientId);
    }

    @Test
    void findById() {
        // given
        ingredientDTO.setRecipe(recipeDTO);

        List<IngredientDTO> ingredientDTOs = new ArrayList<>();
        ingredientDTOs.add(ingredientDTO);
        recipeDTO.setIngredients(ingredientDTOs);

        // when
        when(mockIngredientReactiveRepository.findById(ingredientId)).thenReturn(Mono.just(ingredient));
        when(mockIngredientMapper.toDTO(ingredient)).thenReturn(ingredientDTO);
        IngredientDTO actualIngredientDTO = ingredientServiceImpl.findById(ingredientId).block();

        verify(mockIngredientReactiveRepository).findById(ingredientId);
        verify(mockIngredientMapper).toDTO(ingredient);
        assertEquals(ingredientId, actualIngredientDTO.getId());
    }

    @Test
    void saveIngredientOnRecipe() {
        // given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomId);

        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(ingredientId);
        savedIngredient.setName("TO_SAVE");
        savedIngredient.setUnitOfMeasure(uom);

        UnitOfMeasureDTO uomDTO = new UnitOfMeasureDTO();
        uomDTO.setId(uomId);
        ingredientDTO.setUnitOfMeasure(uomDTO);
        ingredientDTO.setName("TO_SAVE");

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.getIngredients().add(savedIngredient);

        when(mockRecipeReactiveRepository.findById(recipeId)).thenReturn(Mono.just(recipe));
        when(mockIngredientMapper.toEntityIgnoreRecipeChildObjects(any(IngredientDTO.class))).thenReturn(savedIngredient);
        when(mockIngredientMapper.toDTO(any(Ingredient.class))).thenReturn(ingredientDTO);
        when(mockUnitOfMeasureReactiveRepository.findById(anyString())).thenReturn(Mono.just(uom));
        when(mockRecipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));

        // when
        IngredientDTO actualSavedIngredientDTO = ingredientServiceImpl.saveIngredientOnRecipe(recipeId, ingredientDTO).block();

        // then
        assertEquals(savedIngredient.getName(), actualSavedIngredientDTO.getName());
        verify(mockRecipeReactiveRepository).findById(recipeId);
        verify(mockUnitOfMeasureReactiveRepository).findById(anyString());
        verify(mockRecipeReactiveRepository).save(any(Recipe.class));
    }
}