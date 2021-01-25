package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "recipes")
@Document
public class Category {

    @Id
    private String id;
    private String name;

    @DBRef
    private Set<Recipe> recipes = new HashSet<>();

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
