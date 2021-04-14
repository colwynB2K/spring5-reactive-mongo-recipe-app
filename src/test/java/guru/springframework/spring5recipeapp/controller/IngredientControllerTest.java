package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = IngredientController.class)
class IngredientControllerTest {

    @MockBean
    private IngredientService mockIngredientService;

    @MockBean
    private RecipeService mockRecipeService;

    @MockBean
    private UnitOfMeasureService mockUnitOfMeasureService;


    private String recipeId = "2";
    private Flux<UnitOfMeasureDTO> uomList;
    private UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        unitOfMeasureDTO.setId("1");
        unitOfMeasureDTO.setUnit("Spoon");
        unitOfMeasureDTO.setName("Spoon");
        uomList = Flux.just(unitOfMeasureDTO);
    }

    @Test
    void showListForRecipe() throws Exception {
        // given
        RecipeDTO recipeDTO = new RecipeDTO();

        // when
        when(mockRecipeService.findById(anyString())).thenReturn(Mono.just(recipeDTO));

        // then
        webTestClient.get().uri("/recipes/1/ingredients")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
/*                .andExpect(model().attribute("recipe", recipeDTO))
                .andExpect(view().name("recipes/ingredients/list"));*/

        verify(mockRecipeService).findById(anyString());
    }

    @Test
    void showIngredientForRecipe() throws Exception {
        // given
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId("1");
        ingredientDTO.setName("Spinach");

        // when
        when(mockIngredientService.findById(anyString(), anyString())).thenReturn(Mono.just(ingredientDTO));
        when(mockUnitOfMeasureService.findAll()).thenReturn(uomList);

        // then
        webTestClient.get().uri("/recipes/1/ingredients/2")
                .exchange()
//TODO:                .expectStatus().isOk()
                .expectBody(String.class);

/*
                .andExpect(model().attribute("ingredient", ingredientDTO))
                .andExpect(model().attribute("uomList", uomList.collectList().block()))
                .andExpect(view().name("recipes/ingredients/form"));
*/

        verify(mockIngredientService).findById(anyString(), anyString());
        verify(mockUnitOfMeasureService).findAll();
    }

    /*@Test
    void showNewIngredientForm() throws Exception {
        // given
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);

        // when
        when(mockUnitOfMeasureService.findAll()).thenReturn(uomList);

        // then
        mockMvc.perform(get("/recipes/1/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("uomList", uomList.collectList().block()))
                .andExpect(view().name("recipes/ingredients/form"));
        verify(mockUnitOfMeasureService).findAll();
    }

    @Test
    void save() throws Exception {
        // given
        String ingredientId = "3";
        String ingredientName = "Eye of noot";
        String uomId = "666";

        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredientId);
        ingredientDTO.setName(ingredientName);

        unitOfMeasureDTO.setId(uomId);
        ingredientDTO.setUnitOfMeasure(unitOfMeasureDTO);

        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(ingredientDTO);

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);
        recipeDTO.setIngredients(ingredients);

        when(mockIngredientService.saveIngredientOnRecipe(anyString(), any(IngredientDTO.class))).thenReturn(Mono.just(ingredientDTO));

        // when

        // then
        mockMvc.perform(post("/recipes/2/ingredients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", ingredientId)
                        .param("name", ingredientName)
                        .param("uomid", uomId)
                    ).andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/recipes/" + recipeId + "/ingredients/" + ingredientId));
    }

    @Test
    void deleteIngredientFromRecipe() throws Exception {
        // given
        when(mockIngredientService.deleteById(anyString())).thenReturn(Mono.empty());               // By adding .block() in the delete method of the controller, we need to make sure the delete method of the service returns a Mono<Void> or we get an NPE there

        // when
        mockMvc.perform(get("/recipes/2/ingredients/8/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/2/ingredients"));

        // then
        verify(mockIngredientService).deleteById(anyString());
    }*/
}