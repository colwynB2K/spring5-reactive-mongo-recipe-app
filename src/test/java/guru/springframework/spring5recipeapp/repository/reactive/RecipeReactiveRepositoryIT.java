package guru.springframework.spring5recipeapp.repository.reactive;

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
class RecipeReactiveRepositoryIT {

    @Autowired
    private RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    void save() {
        // given
        Recipe recipe = new Recipe();
        recipe.setName("Yummy");

        // when
        recipeReactiveRepository.save(recipe).block();

        // then
        assertEquals(Long.parseLong("1"), recipeReactiveRepository.count().block());
    }

}