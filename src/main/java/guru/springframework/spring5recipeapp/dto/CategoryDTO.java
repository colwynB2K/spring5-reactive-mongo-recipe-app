package guru.springframework.spring5recipeapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private String id;
    private String name;
    // private Set<RecipeDTO> recipes = new HashSet<>(); // Remove bi-directional relationship
}