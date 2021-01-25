package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, IngredientMapper.class, NotesMapper.class})
public interface RecipeMapper {

    @Named("RecipeSetIgnoreChildObjects")
    @IterableMapping(qualifiedByName = "RecipeIgnoreChildObjects")
    Set<RecipeDTO> toDTOSetIgnoreChildObjects(Set<Recipe> recipes);

    @Named("RecipeIgnoreChildObjects")
    @Mappings({
            @Mapping(target = "categories", ignore = true),
            @Mapping(target = "ingredients", ignore = true),
            @Mapping(target = "notes", ignore = true)
    })
    RecipeDTO toDTOIgnoreChildObjects(Recipe recipe);

    @Mappings({
            @Mapping(target = "categories", qualifiedByName = "CategoryListIgnoreRecipes"),
            @Mapping(target = "ingredients", qualifiedByName = "IngredientListIgnoreRecipeChildObjects"),
    })
    RecipeDTO toDTO(Recipe recipe);

    @Named("RecipeSetIgnoreChildObjects")
    @IterableMapping(qualifiedByName = "RecipeIgnoreChildObjects")
    Set<Recipe> toEntitySetIgnoreChildObjects(Set<RecipeDTO> recipeDTOs);

    @Named("RecipeIgnoreChildObjects")
    @Mappings({
            @Mapping(target = "categories", ignore = true),
            @Mapping(target = "ingredients", ignore = true),
            @Mapping(target = "notes", ignore = true)
    })
    Recipe toEntityIgnoreChildObjects(RecipeDTO recipeDTO);

    @Mappings({
            @Mapping(target = "categories", qualifiedByName = "CategoryListIgnoreRecipes"),
            @Mapping(target = "ingredients", qualifiedByName = "IngredientListIgnoreRecipeChildObjects"),
    })
    Recipe toEntity(RecipeDTO recipeDTO);

}
