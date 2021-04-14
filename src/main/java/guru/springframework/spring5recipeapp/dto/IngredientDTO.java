package guru.springframework.spring5recipeapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientDTO {
    private String id;
    private BigDecimal amount;
    private String name;
    private UnitOfMeasureDTO unitOfMeasure;
    private String formattedString;
    // private RecipeDTO recipe;                    // Remove bi-directional relationship
    private String recipeId;

    public String getFormattedString() {
        if (formattedString == null) {
            setFormattedString();
        }

        return this.formattedString;
    }

    private void setFormattedString() {
        StringBuilder formattedString = new StringBuilder();

        if (amount != null) {
            formattedString.append(amount);
        }
        if (unitOfMeasure != null) {
            formattedString.append(' ').append(unitOfMeasure.getUnit()).append(" of ");
        }

        formattedString.append(" ").append(name);

        this.formattedString = formattedString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof IngredientDTO))
            return false;

        IngredientDTO other = (IngredientDTO) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
