package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.exception.ObjectNotFoundException;
import guru.springframework.spring5recipeapp.mapper.RecipeMapper;
import guru.springframework.spring5recipeapp.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeRepositoryServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeRepositoryServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Set<RecipeDTO> findAll() {
        Set<RecipeDTO> recipes = new HashSet<>();
        recipeRepository.findAll()
                .iterator()
                .forEachRemaining(recipe -> recipes.add(recipeMapper.toDTO(recipe)));

        return recipes;
    }

    @Override
    public RecipeDTO findById(String id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No Recipe found for id: " + id + ""));

        return recipeMapper.toDTO(recipe);
    }

    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO);

        Recipe savedRecipe = recipeRepository.save(recipe);

        return recipeMapper.toDTO(savedRecipe);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
