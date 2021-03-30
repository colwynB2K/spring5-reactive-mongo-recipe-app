package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageRepositoryServiceImplTest {

    @Mock
    RecipeReactiveRepository mockRecipeReactiveRepository;

    @Mock
    RecipeMapper mockRecipeMapper;

    @InjectMocks
    private ImageRepositoryServiceImpl imageRepositoryServiceImpl;

    private String recipeId = "1";

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveOnRecipe() throws Exception {
        // given
        MultipartFile expectedMultipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        // when
        when(mockRecipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(mockRecipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
        imageRepositoryServiceImpl.saveOnRecipe(recipeId, expectedMultipartFile).block();

        // then
        verify(mockRecipeReactiveRepository).findById(recipeId);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(mockRecipeReactiveRepository).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(expectedMultipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}