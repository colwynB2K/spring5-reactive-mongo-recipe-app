package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.exception.ObjectNotFoundException;
import guru.springframework.spring5recipeapp.mapper.UnitOfMeasureMapper;
import guru.springframework.spring5recipeapp.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UnitOfMeasureRepositoryServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureMapper unitOfMeasureMapper;

    @Autowired
    public UnitOfMeasureRepositoryServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                              UnitOfMeasureMapper unitOfMeasureMapper) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureMapper = unitOfMeasureMapper;
    }

    @Override
    public UnitOfMeasureDTO getUOMByName(String name) {
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("UOM " + name + " not found!"));

        return unitOfMeasureMapper.toDTO(unitOfMeasure);
    }

    @Override
    public Set<UnitOfMeasureDTO> findAll() {
      return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                        .map(unitOfMeasureMapper::toDTO)
                        .collect(Collectors.toSet());
    }

    @Override
    public UnitOfMeasureDTO findById(String id) {
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No UOM found for id '" + id  + "'"));

        return unitOfMeasureMapper.toDTO(unitOfMeasure);
    }
}
