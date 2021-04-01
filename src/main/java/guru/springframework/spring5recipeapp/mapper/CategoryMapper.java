package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.dto.CategoryDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);

}
