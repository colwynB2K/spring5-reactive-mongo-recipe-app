package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.config.WebConfig;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static guru.springframework.spring5recipeapp.config.WebConfig.API_RECIPES_URI;
import static org.mockito.Mockito.when;

class RouterFunctionTest {

    private WebTestClient webTestClient;

    @Mock
    RecipeService mockRecipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        WebConfig webConfig = new WebConfig();

        RouterFunction<ServerResponse> routerFunction = webConfig.routes(mockRecipeService);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();

        // given
        when(mockRecipeService.findAll()).thenReturn(Flux.just(new RecipeDTO(), new RecipeDTO()));
    }

    @Test
    void testGetRecipes() {
        // given

        // when
        webTestClient.get().uri(API_RECIPES_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        // then
    }

    @Test
    void testGetRecipesWithData() {
        // given

        // when
        webTestClient.get().uri(API_RECIPES_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RecipeDTO.class);

        // then
    }
}
