package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(exclude = "recipes")
@Document
public class Category {

    @Id
    private String id;
    private String name;
    //private Set<Recipe> recipes = new HashSet<>();    // Let's remove this bidirectional reference... as Recipe also points to a category

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Category addRecipe(Recipe recipe) {
        recipe.addCategory(this);

        return this;
    }
}
