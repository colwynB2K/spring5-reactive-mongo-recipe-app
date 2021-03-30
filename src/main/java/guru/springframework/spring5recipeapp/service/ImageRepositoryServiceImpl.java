package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Slf4j
public class ImageRepositoryServiceImpl implements ImageService {

    private RecipeReactiveRepository recipeReactiveRepository;

    @Autowired
    public ImageRepositoryServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveOnRecipe(String recipeId, MultipartFile file) {
        log.debug("Received file: " + file.getName());

        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    try {
                        recipe.setImage(ArrayUtils.toObject(file.getBytes())); // Convert from byte[] to Byte[] and set it on the Recipe before saving it
                    } catch (IOException e) {
                        // TODO: Improve error handling;
                        log.error("IOException: " + e.getMessage());
                    }

                    return recipe;
                });

        recipeReactiveRepository.save(recipeMono.block()).block(); // First block to provide the repository with a Recipe object, second block is to trigger the actual save, probably can be improved upon

        return Mono.empty();
    }
}
