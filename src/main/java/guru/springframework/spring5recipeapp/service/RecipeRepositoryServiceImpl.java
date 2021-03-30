package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RecipeRepositoryServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeRepositoryServiceImpl(RecipeReactiveRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Flux<RecipeDTO> findAll() {
        return recipeRepository.findAll().map(recipeMapper::toDTO);
    }

    @Override
    public Mono<RecipeDTO> findById(String id) {
        return recipeRepository.findById(id).map(recipeMapper::toDTO);
    }

    @Override
    public Mono<RecipeDTO> save(RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO);

        return recipeRepository.save(recipe).map(recipeMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return recipeRepository.deleteById(id);
    }
}
