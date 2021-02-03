package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.mapper.UnitOfMeasureMapper;
import guru.springframework.spring5recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UnitOfMeasureRepositoryServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureMapper unitOfMeasureMapper;

    @Autowired
    public UnitOfMeasureRepositoryServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
                                              UnitOfMeasureMapper unitOfMeasureMapper) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureMapper = unitOfMeasureMapper;
    }

    @Override
    public Mono<UnitOfMeasureDTO> getUOMByName(String name) {
        return unitOfMeasureReactiveRepository.findByName(name).map(unitOfMeasureMapper::toDTO);
    }

    @Override
    public Flux<UnitOfMeasureDTO> findAll() {
      return unitOfMeasureReactiveRepository.findAll().map(unitOfMeasureMapper::toDTO);
    }

    @Override
    public Mono<UnitOfMeasureDTO> findById(String id) {
        return unitOfMeasureReactiveRepository.findById(id).map(unitOfMeasureMapper::toDTO);
    }
}
