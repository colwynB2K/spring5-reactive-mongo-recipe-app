package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.bootstrap.DataInitializer;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repository.reactive.CategoryReactiveRepository;
import guru.springframework.spring5recipeapp.repository.reactive.RecipeReactiveRepository;
import guru.springframework.spring5recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
// Do not load the full Spring Application (Beans via component scanning), but slice the application and only load the Spring Data MongoDB
class UnitOfMeasureServiceIT {

    private final static String UOM_CUP = "Cup";

    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;                                    // Because we are wiring in the actual bean and not a mock and calling the real persistence layer this is an integration test
    @Autowired
    private CategoryReactiveRepository categoryRepository;
    @Autowired
    private RecipeReactiveRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        // Create a new DataInitializer object using the limited Spring Context loaded via @DataMongoTest (which doesn't load services BTW, but we can have the Repository classes)
        DataInitializer dataInitializer = new DataInitializer(categoryRepository, recipeRepository, unitOfMeasureRepository);

        dataInitializer.onApplicationEvent(null);   // trigger the loading of the example data explicitly
    }

    @Test
    void getUOMByName() {
        UnitOfMeasure actualUOM =  unitOfMeasureRepository.findByName(UOM_CUP).block();

        assertEquals(UOM_CUP, actualUOM.getName());
    }
}