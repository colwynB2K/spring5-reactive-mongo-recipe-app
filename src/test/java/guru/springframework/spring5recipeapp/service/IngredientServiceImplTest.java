package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.mapper.IngredientMapper;
import guru.springframework.spring5recipeapp.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private RecipeService mockRecipeService;

    @Mock
    private UnitOfMeasureService mockUnitOfMeasureService;

    @Mock
    IngredientRepository mockIngredientRepository;

    @Mock
    IngredientMapper mockIngredientMapper;

    @InjectMocks
    private IngredientServiceImpl ingredientServiceImpl;

    private Ingredient ingredient;
    private IngredientDTO ingredientDTO;
    private RecipeDTO recipeDTO;

    private String recipeId = "1";
    private String ingredientId = "2";
    private String uomId = "3";

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
        when(mockIngredientRepository.findById(ingredientId)).thenReturn(java.util.Optional.ofNullable(ingredient));
        when(mockIngredientMapper.toDTO(ingredient)).thenReturn(ingredientDTO);
        IngredientDTO actualIngredientDTO = ingredientServiceImpl.findById(ingredientId);

        verify(mockIngredientRepository).findById(ingredientId);
        verify(mockIngredientMapper).toDTO(ingredient);
        assertEquals(ingredientId, actualIngredientDTO.getId());
    }

    @Test
    void saveIngredientOnRecipe() {
        // given
        UnitOfMeasureDTO uomDTO = new UnitOfMeasureDTO();
        uomDTO.setId(uomId);
        ingredientDTO.setUnitOfMeasure(uomDTO);

        RecipeDTO savedRecipeDTO = new RecipeDTO();
        savedRecipeDTO.setId(recipeId);
        savedRecipeDTO.getIngredients().add(new IngredientDTO());
        savedRecipeDTO.getIngredients().iterator().next().setId(ingredientId);

        // when
        when(mockRecipeService.findById(recipeId)).thenReturn(recipeDTO);
        when(mockUnitOfMeasureService.findById(anyString())).thenReturn(uomDTO);
        when(mockRecipeService.save(any(RecipeDTO.class))).thenReturn(savedRecipeDTO);
        IngredientDTO savedIngredientDTO = ingredientServiceImpl.saveIngredientOnRecipe(recipeId, ingredientDTO);

        // then
        assertEquals(ingredientId, savedIngredientDTO.getId());
        verify(mockRecipeService).findById(recipeId);
        verify(mockUnitOfMeasureService).findById(anyString());
        verify(mockRecipeService).save(any(RecipeDTO.class));
    }
}