package guru.springframework.spring5recipeapp.repository.reactive;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    public static final String UOM_ASTRONOMICAL_UNIT_NAME = "Astronomical Unit";
    public static final String UOM_ASTRONOMICAL_UNIT_UNIT = "au";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private UnitOfMeasure unitOfMeasure;

    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();

        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setName(UOM_ASTRONOMICAL_UNIT_NAME);
        unitOfMeasure.setUnit(UOM_ASTRONOMICAL_UNIT_UNIT);
    }

    @Test
    void save() {
        // given

        // when
        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        // then
        assertEquals(Long.parseLong("1"), unitOfMeasureReactiveRepository.count().block());
    }

    @Test
    void findByName() {
        // given
        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        // when
        UnitOfMeasure actualUnitOfMeasure = unitOfMeasureReactiveRepository.findByName(UOM_ASTRONOMICAL_UNIT_NAME).block();

        // then
        assertEquals(UOM_ASTRONOMICAL_UNIT_NAME, actualUnitOfMeasure.getName());
    }

    @Test
    void findByUnit() {
        // given
        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        // when
        UnitOfMeasure actualUnitOfMeasure = unitOfMeasureReactiveRepository.findByUnit(UOM_ASTRONOMICAL_UNIT_UNIT).block();

        // then
        assertEquals(UOM_ASTRONOMICAL_UNIT_UNIT, actualUnitOfMeasure.getUnit());
    }
}