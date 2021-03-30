package guru.springframework.spring5recipeapp.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {
    public Mono<Void> saveOnRecipe(String recipeId, MultipartFile file);
}
