package guru.springframework.spring5recipeapp.dto;

import guru.springframework.spring5recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDTO {
    private String id;

    @NotBlank
    private String cookInstructions;

    private String description;

    @Min(1)                                     // Require minimum 1 min cooking time
    @Max(999)
    private Integer cookTimeMins;

    private Difficulty difficulty;
    private Byte[] image;

    @NotBlank
    @Size(min = 3, max = 255)                   // Hibernate by default creates a VARCHAR(255) so we use that as allowed max length
    private String name;

    @Min(1)                                     // Require minimum 1 min prep time
    @Max(999)
    private Integer prepTimeMins;

    private String source;

    @URL
    private String url;

    private String yield;
    private List<CategoryDTO> categories = new ArrayList<>();
    private List<IngredientDTO> ingredients = new ArrayList<>();
    private NotesDTO notes;
}
