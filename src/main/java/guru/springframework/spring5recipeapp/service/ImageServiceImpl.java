package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private RecipeService recipeService;
    private RecipeRepository recipeRepository;
    private RecipeMapper recipeMapper;

    @Autowired
    public ImageServiceImpl(RecipeService recipeService, RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public void saveOnRecipe(String recipeId, MultipartFile file) {
        log.debug("Received file: " + file.getName());

        RecipeDTO recipeDTO = recipeService.findById(recipeId);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);

        try {
            recipe.setImage(ArrayUtils.toObject(file.getBytes())); // Convert from byte[] to Byte[] and set it on the Recipe before saving it
        } catch (IOException e) {
            // TODO: Improve error handling
            log.error("IOException: " + e.getMessage());
        }

        recipeRepository.save(recipe);
    }
}
