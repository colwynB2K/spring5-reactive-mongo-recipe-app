package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @Mock
    private RecipeService mockRecipeService;

    @InjectMocks
    private IndexController indexController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController)
                                    .setControllerAdvice(new ControllerExceptionHandler())
                                    .build();
    }

    @Test
    public void testIndex() throws Exception {
        // given
        RecipeDTO expectedRecipe1 = new RecipeDTO();
        expectedRecipe1.setName("BLAAT");

        RecipeDTO expectedRecipe2 = new RecipeDTO();
        expectedRecipe2.setName("MEKKER");

        when(mockRecipeService.findAll()).thenReturn(Flux.just(expectedRecipe1, expectedRecipe2));

        // when

        // then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(mockRecipeService, times(1)).findAll();
    }
}