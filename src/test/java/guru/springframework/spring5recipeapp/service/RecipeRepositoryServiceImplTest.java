package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.exception.ObjectNotFoundException;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeRepositoryServiceImplTest {

    private final static String ID = "1";

    @Mock
    private RecipeRepository mockRecipeRepository;

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
        Set<Recipe> expectedRecipes = new HashSet<>();
        expectedRecipes.add(new Recipe());
        when(mockRecipeMapper.toDTO(any(Recipe.class))).thenReturn(recipeDTO);
        when(mockRecipeRepository.findAll()).thenReturn(expectedRecipes);

        // when
        Set<RecipeDTO> actualRecipes = recipeRepositoryServiceImpl.findAll();                  // When calling the findAll() method on the recipeRepositoryServiceImpl (object under test)

        // then
        verify(mockRecipeRepository, times(1)).findAll();   // Verify that the findAll() method on the (mocked) repository was called precisely 1 time
        assertEquals(actualRecipes.size(), 1);                                    // Assert that the resulting Recipe Set has only 1 Recipe in it
    }

    @Test
    void findById() {
        // given
        when(mockRecipeRepository.findById(ID)).thenReturn(java.util.Optional.ofNullable(recipe));
        when(mockRecipeMapper.toDTO(any(Recipe.class))).thenReturn(recipeDTO);

        // when
        RecipeDTO actualRecipe = recipeRepositoryServiceImpl.findById(ID);

        // then
        assertNotNull(actualRecipe);
        verify(mockRecipeRepository).findById(ID);
    }

    @Test
    void findById_Should_Throw_ObjectNotFoundException_For_Unknown_Id() {
        // given
        when(mockRecipeRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        // when
        ObjectNotFoundException e = assertThrows(ObjectNotFoundException.class, () -> recipeRepositoryServiceImpl.findById(ID));

        // then
        assertEquals("No Recipe found for id: " + ID, e.getMessage());
    }

    @Test
    void deleteById() {
        // no 'when' statement for the mockRecipeRepository, since the delete method has void return type

        // when
        recipeRepositoryServiceImpl.deleteById(ID);

        // then
        verify(mockRecipeRepository).deleteById(ID);
    }
}