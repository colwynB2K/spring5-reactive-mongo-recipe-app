package guru.springframework.spring5recipeapp.repository.reactive;

import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepositoryIT {

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    private Category category;

    private final static String CATEGORY_NAME_ASIAN = "Asian";

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();

        category = new Category();
        category.setName(CATEGORY_NAME_ASIAN);
    }

    @Test
    void save() {
        // given

        // when
        categoryReactiveRepository.save(category).block();              // Don't forget to add .block, otherwise you basically just queue this save event, but don't act on it yet
        Long actualCount = categoryReactiveRepository.count().block();

        // then
        assertEquals(Long.parseLong("1"), actualCount);
    }

    void findByName() {
        // given
        categoryReactiveRepository.save(category).block();              // Don't forget to add .block, otherwise you basically just queue this save event, but don't act on it yet

        // when
        Category actualCategory = categoryReactiveRepository.findByName(CATEGORY_NAME_ASIAN).block();

        // then
        assertNotNull(actualCategory.getId());
    }
}