package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.service.RecipeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = IndexController.class)
class IndexControllerTest {

    @MockBean
    private RecipeService mockRecipeService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testIndex() {
        // given
        RecipeDTO expectedRecipe1 = new RecipeDTO();
        expectedRecipe1.setName("Fajita");

        RecipeDTO expectedRecipe2 = new RecipeDTO();
        expectedRecipe2.setName("Taco");

        when(mockRecipeService.findAll()).thenReturn(Flux.just(expectedRecipe1, expectedRecipe2));

        // when

        // then
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseBody = Objects.requireNonNull(response.getResponseBody());
                    assertTrue(responseBody.contains("Fajita"));
                    assertTrue(responseBody.contains("Taco"));
                });

        verify(mockRecipeService, times(1)).findAll();
    }
}