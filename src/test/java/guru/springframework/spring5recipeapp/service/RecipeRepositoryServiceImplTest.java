package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeRepositoryServiceImplTest {

    private final static String ID = "1";

    @Mock
    private RecipeReactiveRepository mockRecipeReactiveRepository;

    @Mock
    private RecipeMapper mockRecipeMapper;

    @InjectMocks
    private RecipeRepositoryServiceImpl recipeRepositoryServiceImpl;

    private Recipe recipe;
    private RecipeDTO recipeDTO;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(ID);

        recipeDTO = new RecipeDTO();
        recipeDTO.setId(ID);
    }

    @Test
    void findAll() {
        // given
        when(mockRecipeReactiveRepository.findAll()).thenReturn(Flux.just(recipe));
        when(mockRecipeMapper.toDTO(any(Recipe.class))).thenReturn(recipeDTO);

        // when
        List<RecipeDTO> actualRecipes = recipeRepositoryServiceImpl.findAll().collectList().block();                  // When calling the findAll() method on the recipeRepositoryServiceImpl (object under test)

        // then
        verify(mockRecipeReactiveRepository).findAll();   // Verify that the findAll() method on the (mocked) repository was called precisely 1 time
        assertEquals(actualRecipes.size(), 1);                                    // Assert that the resulting Recipe Set has only 1 Recipe in it
    }

    @Test
    void findById() {
        // given
        when(mockRecipeReactiveRepository.findById(ID)).thenReturn(Mono.just(recipe));
        when(mockRecipeMapper.toDTO(any(Recipe.class))).thenReturn(recipeDTO);

        // when
        RecipeDTO actualRecipe = recipeRepositoryServiceImpl.findById(ID).block();

        // then
        assertNotNull(actualRecipe);
        verify(mockRecipeReactiveRepository).findById(ID);
    }

    @Test
    void deleteById() {
        // no 'when' statement for the mockRecipeRepository, since the delete method has void return type

        // when
        recipeRepositoryServiceImpl.deleteById(ID);

        // then
        verify(mockRecipeReactiveRepository).deleteById(ID);
    }
}