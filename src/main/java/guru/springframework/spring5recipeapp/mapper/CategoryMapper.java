package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.dto.CategoryDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "recipes", qualifiedByName = "RecipeSetIgnoreChildObjects")
    })
    CategoryDTO toDTO(Category category);

    @Named("CategoryIgnoreRecipes")
    @Mapping(target = "recipes", ignore = true)
    CategoryDTO toDTOIgnoreRecipes(Category category);

    @Named("CategoryListIgnoreRecipes")
    @IterableMapping(qualifiedByName = "CategoryIgnoreRecipes")
    List<CategoryDTO> toDTOListIgnoreRecipes(List<Category> categories);

    @Mappings({
            @Mapping(target = "recipes", qualifiedByName = "RecipeSetIgnoreChildObjects")
    })
    Category toEntity(CategoryDTO categoryDTO);

    @Named("CategoryIgnoreRecipes")
    @Mapping(target = "recipes", ignore = true)
    Category toEntityIgnoreRecipes(CategoryDTO categoryDTO);

    @Named("CategoryListIgnoreRecipes")
    @IterableMapping(qualifiedByName = "CategoryIgnoreRecipes")
    List<Category> toEntityListIgnoreRecipes(List<CategoryDTO> categoryDTOs);

}
