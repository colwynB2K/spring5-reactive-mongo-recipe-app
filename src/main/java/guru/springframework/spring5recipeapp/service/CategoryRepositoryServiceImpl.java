package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.dto.CategoryDTO;
import guru.springframework.spring5recipeapp.mapper.CategoryMapper;
import guru.springframework.spring5recipeapp.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CategoryRepositoryServiceImpl implements CategoryService {

    final private CategoryRepository categoryRepository;
    final private CategoryMapper categoryMapper;

    @Autowired
    public CategoryRepositoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category =  categoryRepository.findByName(name).orElseThrow(() -> new RuntimeException("Category " + name + " not " +
                "found!"));

        return categoryMapper.toDTO(category);
    }

    @Override
    public Set<CategoryDTO> findAll() {
        Set<CategoryDTO> categoryDTOs = new HashSet<>();
        categoryRepository.findAll().forEach(category -> categoryDTOs.add(categoryMapper.toDTO(category)));

        return categoryDTOs;
    }
}
