package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitOfMeasureMapper {

    UnitOfMeasureDTO toDTO(UnitOfMeasure UnitOfMeasure);

    UnitOfMeasure toEntity(UnitOfMeasureDTO UnitOfMeasureDTO);

}
