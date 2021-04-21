package guru.springframework.spring5recipeapp.config;

import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class WebConfig {

    public static final String API_RECIPES_URI = "/api/recipes";

    @Bean
    public RouterFunction<ServerResponse> routes(RecipeService recipeService) {
        return RouterFunctions.route(GET(API_RECIPES_URI),
                    serverRequest -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(recipeService.findAll(), RecipeDTO.class)
        );
    }
}
