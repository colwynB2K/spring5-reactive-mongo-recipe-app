package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.CategoryDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.exception.ObjectNotFoundException;
import guru.springframework.spring5recipeapp.service.CategoryService;
import guru.springframework.spring5recipeapp.service.ImageService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = RecipeController.class)
class RecipeControllerTest {

    @MockBean
    private RecipeService mockRecipeService;

    @MockBean
    private CategoryService mockCategoryService;

    @MockBean
    ImageService mockImageService;

    private RecipeDTO recipeDTO;
    private Flux<RecipeDTO> recipeDTOs;
    private Flux<CategoryDTO> categoryDTOs;
    private static final String ID = "1";

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        recipeDTO = new RecipeDTO();
        recipeDTO.setId(ID);
        recipeDTO.setName("Spreddel");
        recipeDTOs = Flux.just(recipeDTO);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTOs = Flux.just(categoryDTO);
    }

    @Test
    void showRecipeDetail() throws Exception {
        // given
        when(mockRecipeService.findById(anyString())).thenReturn(Mono.just(recipeDTO));

        // whwn
        webTestClient.get().uri("/recipes/" + ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = Objects.requireNonNull(response.getResponseBody());
                    assertTrue(responseBody.contains("Spreddel"));
                });
                /*.andExpect(model().attribute("recipe", equalTo(recipeDTO)))
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_DETAIL));*/

        // then
        verify(mockRecipeService).findById(ID);
    }

    @Test
    void showRecipeDetail_Should_Return_A_404_For_Unknown_Id() throws Exception {
        // given
        when(mockRecipeService.findById(anyString())).thenThrow(ObjectNotFoundException.class);

        // when
        EntityExchangeResult<byte[]> result =  webTestClient.get().uri("/recipes/" + ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .returnResult();
                //.andExpect(view().name(RecipeController.VIEWS_404));
    }

    /*@Test
    void showRecipeForm_For_Specified_Recipe_Id() throws Exception {
        // given
        when(mockRecipeService.findById(anyString())).thenReturn(Mono.just(recipeDTO));
        when(mockCategoryService.findAll()).thenReturn(categoryDTOs);

        // when
        mockMvc.perform(get("/recipes/edit/" + ID))
                .andExpect(model().attribute("recipe", equalTo(recipeDTO)))
                .andExpect(model().attribute("categories", equalTo(categoryDTOs)))
                .andExpect(status().isOk())
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_FORM));

        // then
        verify(mockRecipeService).findById(ID);
        verify(mockCategoryService).findAll();
    }

    @Test
    void showRecipeForm_When_RecipeId_Is_Null() throws Exception {
        // given
        when(mockCategoryService.findAll()).thenReturn(categoryDTOs);

        // when
        mockMvc.perform(get("/recipes/edit"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("categories", equalTo(categoryDTOs)))
                .andExpect(status().isOk())
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_FORM));

        // then
        verify(mockCategoryService).findAll();
    }

    @Test
    void saveRecipe() throws Exception {
        // given
        when(mockRecipeService.save(any(RecipeDTO.class))).thenReturn(Mono.just(recipeDTO));

        // when
        mockMvc.perform(
                    post("/recipes")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "")
                    .param("name", "some name")
                    .param("cookInstructions", "some cooking instructions")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_REDIRECT + ID));

        // then
        verify(mockRecipeService).save(any(RecipeDTO.class));
    }

    @Test
    void saveRecipeFormValidationFail() throws Exception {
        // when
        mockMvc.perform(
                post("/recipes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")                        // Provide no description nor cooking instructions
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_FORM));

    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/recipes/" + ID + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(RecipeController.VIEWS_ROOT_REDIRECT));

        verify(mockRecipeService).deleteById(recipeDTO.getId());
    }

    @Test
    void showImageForm() throws Exception {
        // given
        when(mockRecipeService.findById(anyString())).thenReturn(Mono.just(recipeDTO));

        // when

        // then
        mockMvc.perform(get("/recipes/1/image/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", equalTo(recipeDTO)))
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_IMAGES_FORM));

        verify(mockRecipeService).findById(anyString());
    }

    @Test
    void handleImagePost() throws Exception {
        // given
        Long recipeId = 1L;
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());

        // then
        mockMvc.perform(multipart("/recipes/" + recipeId + "/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "/recipes/1"))
                .andExpect(view().name(RecipeController.VIEWS_RECIPES_REDIRECT + recipeId));

        verify(mockImageService).saveOnRecipe(anyString(), any(MultipartFile.class));
    }

    @Test
    void showImage() throws Exception {
        //given
        String recipeId = "1";
        String s = "fake Image content";
        recipeDTO.setImage(ArrayUtils.toObject(s.getBytes()));

        when(mockRecipeService.findById(anyString())).thenReturn(Mono.just(recipeDTO));

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/recipes/" + recipeId + "/image"))
                                                .andExpect(status().isOk())
                                                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        // then
        assertEquals(s.getBytes().length, responseBytes.length);
    }*/
}