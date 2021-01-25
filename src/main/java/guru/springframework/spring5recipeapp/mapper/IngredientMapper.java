package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface IngredientMapper {

    IngredientDTO toDTO(Ingredient ingredient);

    @Named("IngredientIgnoreRecipeChildObjects")
    @Mappings({
            @Mapping(target = "recipe.categories", ignore = true),
            @Mapping(target = "recipe.ingredients", ignore = true),
            @Mapping(target = "recipe.notes", ignore = true)
    })
    IngredientDTO toDTOIgnoreRecipeChildObjects(Ingredient ingredient);

    @Named("IngredientListIgnoreRecipeChildObjects")
    @IterableMapping(qualifiedByName = "IngredientIgnoreRecipeChildObjects")
    List<IngredientDTO> toDTOSetIgnoreRecipeChildObjects(List<Ingredient> ingredients);

    @Named("IngredientIgnoreRecipeChildObjects")
    @Mappings({
            @Mapping(target = "recipe.categories", ignore = true),
            @Mapping(target = "recipe.ingredients", ignore = true),
            @Mapping(target = "recipe.notes", ignore = true)
    })
    Ingredient toEntityIgnoreRecipeChildObjects(IngredientDTO ingredientDTO);

    @Named("IngredientListIgnoreRecipeChildObjects")
    @IterableMapping(qualifiedByName = "IngredientIgnoreRecipeChildObjects")
    List<Ingredient> toEntitySetIgnoreRecipeChildObjects(List<IngredientDTO> ingredientDTOs);
}
