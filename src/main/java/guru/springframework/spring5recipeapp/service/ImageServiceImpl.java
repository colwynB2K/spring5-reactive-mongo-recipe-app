package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.RecipeRepository;
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
public class ImageServiceImpl implements ImageService {

    private RecipeReactiveRepository recipeRepository;
    private RecipeMapper recipeMapper;

    @Autowired
    public ImageServiceImpl(RecipeReactiveRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Mono<Void> saveOnRecipe(String recipeId, MultipartFile file) {
        log.debug("Received file: " + file.getName());

        Recipe recipe = recipeRepository.findById(recipeId).block();
        try {
            recipe.setImage(ArrayUtils.toObject(file.getBytes())); // Convert from byte[] to Byte[] and set it on the Recipe before saving it
        } catch (IOException e) {
            // TODO: Improve error handling
            log.error("IOException: " + e.getMessage());
        }

        recipeRepository.save(recipe);

        return Mono.empty();
    }
}
