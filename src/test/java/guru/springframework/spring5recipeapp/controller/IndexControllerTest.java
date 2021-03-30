package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService mockRecipeService;

    @Mock
    private Model mockModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(mockRecipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {
        // given
        String expectedResult = "index";
        RecipeDTO expectedRecipe1 = new RecipeDTO();
        expectedRecipe1.setName("BLAAT");

        RecipeDTO expectedRecipe2 = new RecipeDTO();
        expectedRecipe2.setName("MEKKER");

        when(mockRecipeService.findAll()).thenReturn(Flux.just(expectedRecipe1, expectedRecipe2));

        ArgumentCaptor<Flux<RecipeDTO>> recipeSetArgumumentCaptor = ArgumentCaptor.forClass(Flux.class);

        // when
        String actualResult = indexController.getIndexPage(mockModel);

        // then
        verify(mockRecipeService, times(1)).findAll();
        verify(mockModel, times(1)).addAttribute(eq("recipes"), recipeSetArgumumentCaptor.capture());
        List<RecipeDTO> actualRecipes = recipeSetArgumumentCaptor.getValue().collectList().block();
        assertEquals(2, actualRecipes.size());
        assertEquals(expectedResult, actualResult);
    }
}