package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.CategoryDTO;
import guru.springframework.spring5recipeapp.mapper.CategoryMapper;
import guru.springframework.spring5recipeapp.repository.reactive.CategoryReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CategoryRepositoryServiceImpl implements CategoryService {

    final private CategoryReactiveRepository categoryReactiveRepository;
    final private CategoryMapper categoryMapper;

    @Autowired
    public CategoryRepositoryServiceImpl(CategoryReactiveRepository categoryReactiveRepository, CategoryMapper categoryMapper) {
        this.categoryReactiveRepository = categoryReactiveRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Mono<CategoryDTO> getCategoryByName(String name) {
        return categoryReactiveRepository.findByName(name).map(categoryMapper::toDTO);
    }

    @Override
    public Flux<CategoryDTO> findAll() {
       return categoryReactiveRepository.findAll().map(categoryMapper::toDTO);
    }
}
