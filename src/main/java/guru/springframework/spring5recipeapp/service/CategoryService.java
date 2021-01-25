package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.CategoryDTO;

import java.util.Set;

public interface CategoryService {

    CategoryDTO getCategoryByName(String name);

    Set<CategoryDTO> findAll();
}
