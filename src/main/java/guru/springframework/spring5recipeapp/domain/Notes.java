package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
public class Notes {

    @Id
    private String id = UUID.randomUUID().toString();
    private String notes;
    //private Recipe recipe;        // Remove bi-directional relationship

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", notes='" + notes + '\'' +
                '}';
    }
}
