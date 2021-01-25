package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UnitOfMeasure {

    @Id
    private String id;
    private String name;
    private String unit;
}
