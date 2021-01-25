package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository mockRecipeRepository;

    @Mock
    RecipeService mockRecipeService;

    @Mock
    RecipeMapper mockRecipeMapper;

    @InjectMocks
    private ImageServiceImpl imageServiceImpl;

    private String recipeId = "1";

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveOnRecipe() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);

        // when
        when(mockRecipeService.findById(anyString())).thenReturn(recipeDTO);
        when(mockRecipeMapper.toEntity(any(RecipeDTO.class))).thenReturn(recipe);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        imageServiceImpl.saveOnRecipe(recipeId, mockMultipartFile);

        // then
        verify(mockRecipeRepository).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(mockMultipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}