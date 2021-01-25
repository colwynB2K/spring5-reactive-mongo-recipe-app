package guru.springframework.spring5recipeapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public void saveOnRecipe(String recipeId, MultipartFile file);
}
