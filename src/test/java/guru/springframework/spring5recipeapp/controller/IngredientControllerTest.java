package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService mockIngredientService;

    @Mock
    private RecipeService mockRecipeService;

    @Mock
    private UnitOfMeasureService mockUnitOfMeasureService;

    @InjectMocks
    private IngredientController ingredientController;

    private MockMvc mockMvc;

    private String recipeId = "2";
    private Flux<UnitOfMeasureDTO> uomList;
    private UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();


        uomList = Flux.just(unitOfMeasureDTO);
    }

    @Test
    void showListForRecipe() throws Exception {
        // given
        RecipeDTO recipeDTO = new RecipeDTO();


        // when
        when(mockRecipeService.findById(anyString())).thenReturn(Mono.just(recipeDTO));

        // then
        mockMvc.perform(get("/recipes/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", recipeDTO))
                .andExpect(view().name("recipes/ingredients/list"));
        verify(mockRecipeService).findById(anyString());
    }

    @Test
    void showIngredientForRecipe() throws Exception {
        // given
        IngredientDTO ingredientDTO = new IngredientDTO();

        // when
        when(mockIngredientService.findById(anyString())).thenReturn(Mono.just(ingredientDTO));
        when(mockUnitOfMeasureService.findAll()).thenReturn(uomList);

        // then
        mockMvc.perform(get("/recipes/1/ingredients/2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ingredient", ingredientDTO))
                .andExpect(model().attribute("uomList", uomList.collectList().block()))
                .andExpect(view().name("recipes/ingredients/form"));
        verify(mockIngredientService).findById(anyString());
        verify(mockUnitOfMeasureService).findAll();
    }

    @Test
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
    }
}