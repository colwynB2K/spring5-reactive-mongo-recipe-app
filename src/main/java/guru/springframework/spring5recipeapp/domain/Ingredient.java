package guru.springframework.spring5recipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Ingredient {

    @Id
    private String id = UUID.randomUUID().toString();
    private BigDecimal amount;
    private String name;

    @DBRef
    private UnitOfMeasure unitOfMeasure;
    //private Recipe recipe;                // Commented out this bidirectional reference

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public Ingredient(String name, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Ingredient))
            return false;

        Ingredient other = (Ingredient) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", unitOfMeasure=" + unitOfMeasure +
                '}';
    }
}
