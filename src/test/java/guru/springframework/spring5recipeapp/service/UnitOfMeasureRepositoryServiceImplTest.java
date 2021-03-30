package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.mapper.UnitOfMeasureMapper;
import guru.springframework.spring5recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureRepositoryServiceImplTest {

    @Mock
    private UnitOfMeasureMapper mockUnitOfMeasureMapper;

    @Mock
    private UnitOfMeasureReactiveRepository mockUnitOfMeasureReactiveRepository;

    @InjectMocks
    private UnitOfMeasureRepositoryServiceImpl unitOfMeasureRepositoryServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getUOMByName() {
    }

    @Test
    void findAll() {
        // given
        Set<UnitOfMeasure> uoms = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");
        uoms.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");
        uoms.add(uom2);

        UnitOfMeasureDTO uomDTO = new UnitOfMeasureDTO();
        uomDTO.setId("1");

        when(mockUnitOfMeasureMapper.toDTO(any(UnitOfMeasure.class))).thenReturn(uomDTO);
        when(mockUnitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        // when
        List<UnitOfMeasureDTO> actualUomDTOs = unitOfMeasureRepositoryServiceImpl.findAll().collectList().block();

        // then
        assertEquals(2, actualUomDTOs.size());
        verify(mockUnitOfMeasureReactiveRepository).findAll();
    }

}