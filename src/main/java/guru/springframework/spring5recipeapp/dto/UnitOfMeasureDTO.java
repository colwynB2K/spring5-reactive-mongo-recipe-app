package guru.springframework.spring5recipeapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureDTO {
    private String id;
    private String name;
    private String unit;
}
