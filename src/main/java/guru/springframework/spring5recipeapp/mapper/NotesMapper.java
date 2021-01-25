package guru.springframework.spring5recipeapp.mapper;

import guru.springframework.spring5recipeapp.domain.Notes;
import guru.springframework.spring5recipeapp.dto.NotesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotesMapper {

    NotesDTO toDTO(Notes Notes);

    Notes toEntity(NotesDTO NotesDTO);
}
