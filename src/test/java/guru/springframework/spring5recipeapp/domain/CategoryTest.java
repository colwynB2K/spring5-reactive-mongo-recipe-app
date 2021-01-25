package guru.springframework.spring5recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        String id = "4";

        category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    void getName() {
    }

    @Test
    void getRecipes() {
    }
}