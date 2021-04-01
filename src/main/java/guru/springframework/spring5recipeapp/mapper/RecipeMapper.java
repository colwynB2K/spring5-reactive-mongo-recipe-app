package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, IngredientMapper.class, NotesMapper.class})
public interface RecipeMapper {

    @Named("RecipeIgnoreChildObjects")
    @Mappings({
            @Mapping(target = "categories", ignore = true),
            @Mapping(target = "ingredients", ignore = true),
            @Mapping(target = "notes", ignore = true)
    })
    RecipeDTO toDTOIgnoreChildObjects(Recipe recipe);

    @Mappings({
            @Mapping(target = "ingredients", qualifiedByName = "IngredientListIgnoreRecipeChildObjects"),
    })
    RecipeDTO toDTO(Recipe recipe);

    @Named("RecipeIgnoreChildObjects")
    @Mappings({
            @Mapping(target = "categories", ignore = true),
            @Mapping(target = "ingredients", ignore = true),
            @Mapping(target = "notes", ignore = true)
    })
    Recipe toEntityIgnoreChildObjects(RecipeDTO recipeDTO);

    @Mappings({
            @Mapping(target = "ingredients", qualifiedByName = "IngredientListIgnoreRecipeChildObjects"),
    })
    Recipe toEntity(RecipeDTO recipeDTO);

}
