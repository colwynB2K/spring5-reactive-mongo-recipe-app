package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;

import java.util.Set;

public interface UnitOfMeasureService {

    UnitOfMeasureDTO getUOMByName(String name);

    Set<UnitOfMeasureDTO> findAll();

    UnitOfMeasureDTO findById(String id);
}
