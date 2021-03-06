package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"categories", "notes"})
// Excluded categories and notes, but left in ingredients for equals and hashCode generation as it seems an essential
// part of the recipe
@Document(collection = "recipe")
public class Recipe {

    @Id
    private String id;
    private String cookInstructions;
    private String description;
    private Integer cookTimeMins;
    private Difficulty difficulty;
    private Byte[] image;
    private String name;
    private Integer prepTimeMins;
    private String source;
    private String url;
    private String yield;

    private List<Category> categories = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private Notes notes;

    public Recipe addCategory(Category category) {
        this.categories.add(category);
//        category.getRecipes().add(this);          // Commented out this bidirectional reference

        return this;
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        //ingredient.setRecipe(this);                   // Commented out this bidirectional reference
       // ingredient.setRecipeId(this.id); // Can't do that if the recipe wasn't saved yet, because it will have id null, BUMMER

        return this;
    }

    public Recipe addIngredients(List<Ingredient> ingredients) {
        //ingredients.stream().forEach(ingredient -> ingredient.setRecipe(this));       // Commented out this bidirectional reference
        //ingredients.stream().forEach(ingredient -> ingredient.setRecipeId(this.id));       // Commented out this bidirectional reference
        this.setIngredients(ingredients);

        return this;
    }

    public Recipe addNotes(Notes notes) {
        this.setNotes(notes);
        //notes.setRecipe(this);                        // Commented out this bidirectional reference

        return this;
    }
}
