package guru.springframework.spring5recipeapp.bootstrap;

import guru.springframework.spring5recipeapp.domain.*;

import guru.springframework.spring5recipeapp.repository.CategoryRepository;
import guru.springframework.spring5recipeapp.repository.RecipeRepository;
import guru.springframework.spring5recipeapp.repository.UnitOfMeasureRepository;
import guru.springframework.spring5recipeapp.repository.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Use this for adding example data to a test database
@Component
@Slf4j
@Profile("default")
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Autowired
    public DataInitializer(CategoryRepository categoryRepository,
                           RecipeRepository recipeRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Loading Bootstrap Data...");
        loadCategories();
        loadUom();
        recipeRepository.saveAll(getRecipes());

        // Some ugly temporary test code here
        log.error("########");
        log.error("Count: " + unitOfMeasureReactiveRepository.count().block()); // .block() will make sure the Mono publisher will return its stream content now
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setName("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setName("Fast Food");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setName("Good Belgian Cooking");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setName("Fast Italian");
        categoryRepository.save(cat4);

        Category cat5 = new Category();
        cat5.setName("Mexican");
        categoryRepository.save(cat5);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setName("Cup");
        uom1.setUnit("Cup");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setName("Dash");
        uom2.setUnit("Dash");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setName("Drop");
        uom3.setUnit("Drop");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setName("Gram");
        uom4.setUnit("g");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setName("Kilogram");
        uom5.setUnit("kg");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setName("Milliliter");
        uom6.setUnit("ml");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setName("Liter");
        uom7.setUnit("l");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setName("Ounce");
        uom8.setUnit("oz");
        unitOfMeasureRepository.save(uom8);

        UnitOfMeasure uom9 = new UnitOfMeasure();
        uom9.setName("Pinch");
        uom9.setUnit("Pinch");
        unitOfMeasureRepository.save(uom9);

        UnitOfMeasure uom10 = new UnitOfMeasure();
        uom10.setName("Tablespoon");
        uom10.setUnit("Tablespoon");
        unitOfMeasureRepository.save(uom10);

        UnitOfMeasure uom11 = new UnitOfMeasure();
        uom11.setName("Teaspoon");
        uom11.setUnit("Teaspoon");
        unitOfMeasureRepository.save(uom11);
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure cup = getUnitOfMeasureByName("Cup");
        UnitOfMeasure dash = getUnitOfMeasureByName("Dash");
        UnitOfMeasure tableSpoon = getUnitOfMeasureByName("Tablespoon");
        UnitOfMeasure teaSpoon = getUnitOfMeasureByName("Teaspoon");

        Category mexicanCategory = getCategoryByName("Mexican");

        // Perfect Guacamole recipe
        Recipe perfectGuacamoleRecipe = new Recipe();
        perfectGuacamoleRecipe.addCategory(mexicanCategory);
        perfectGuacamoleRecipe.setName("Perfect Guacamole");
        perfectGuacamoleRecipe.setSource("Simply Recipes Website");
        perfectGuacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamoleRecipe.setDescription("The best guacamole keeps it simple: just ripe avocados, salt, a " +
                "squeeze of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next " +
                "party or spoon it on top of tacos for an easy dinner upgrade.");
       /* File imageFile = new File();
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        perfectGuacamoleRecipe.setImage();*/
        perfectGuacamoleRecipe.setPrepTimeMins(10);
        perfectGuacamoleRecipe.setCookTimeMins(0);
        perfectGuacamoleRecipe.setYield("Serves 2-4");
        perfectGuacamoleRecipe.setDifficulty(Difficulty.EASY);

        perfectGuacamoleRecipe
                .addIngredient(new Ingredient("ripe avocado", new BigDecimal(2)))
                .addIngredient(new Ingredient("salt", new BigDecimal(0.25), teaSpoon))
                .addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(1), tableSpoon))
                .addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(0.25),
                        cup))
                .addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(1)))
                .addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2)
                        , tableSpoon))
                .addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(1), dash))
                .addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(0.5)))
                .addIngredient(new Ingredient("Red radishes or jicama, to garnish"))
                .addIngredient(new Ingredient("Tortilla chips, to serve", new BigDecimal(2)));

        perfectGuacamoleRecipe.setCookInstructions("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove" +
                " the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                "(See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should " +
                "be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the " +
                "lime juice will provide some balance to the richness of the avocado and will help delay the avocados" +
                " from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their " +
                "hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree " +
                "of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. " +
                "Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it" +
                " just before serving.\n" +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the" +
                " guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes " +
                "oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        recipes.add(perfectGuacamoleRecipe);
        log.info("Loaded: Perfect Guacamole");

        // Spicy Grilled Chicken Tacos recipe
        Recipe spicyGrilledChickenTacosRecipe = new Recipe();

        spicyGrilledChickenTacosRecipe.addCategory(mexicanCategory);
        spicyGrilledChickenTacosRecipe.setName("Spicy Grilled Chicken Tacos");
        spicyGrilledChickenTacosRecipe.setSource("Simply Recipes Website");
        spicyGrilledChickenTacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyGrilledChickenTacosRecipe.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready" +
                " in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
       /* File imageFile = new File();
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        spicyGrilledChickenTacosRecipe.setImage();*/
        spicyGrilledChickenTacosRecipe.setPrepTimeMins(20);
        spicyGrilledChickenTacosRecipe.setCookTimeMins(15);
        spicyGrilledChickenTacosRecipe.setYield("Serves 4-6");
        spicyGrilledChickenTacosRecipe.setDifficulty(Difficulty.MODERATE);

        spicyGrilledChickenTacosRecipe
                .addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoon))
                .addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaSpoon))
                .addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaSpoon))
                .addIngredient(new Ingredient("sugar", new BigDecimal(1), teaSpoon))
                .addIngredient(new Ingredient("salt", new BigDecimal(0.5), teaSpoon))
                .addIngredient(new Ingredient("clove garlic, finely chopped", new BigDecimal(1)))
                .addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoon))
                .addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoon))
                .addIngredient(new Ingredient("olive oil", new BigDecimal(2), tableSpoon))
                .addIngredient(new Ingredient("skinless, boneless chicken thighs (1 1/4 " + "pounds)",
                        new BigDecimal(5)))
                .addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8)))
                .addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cup))
                .addIngredient(new Ingredient("medium ripe avocados, sliced", new BigDecimal(2)))
                .addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4)))
                .addIngredient(new Ingredient("pint cherry tomatoes, halved", new BigDecimal(0.5)))
                .addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(0.25)))
                .addIngredient(new Ingredient("roughly chopped cilantro", new BigDecimal(1)))
                .addIngredient(new Ingredient("cup sour cream ", new BigDecimal(0.5), cup))
                .addIngredient(new Ingredient("milk", new BigDecimal(0.25), cup))
                .addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(1)));

        spicyGrilledChickenTacosRecipe.setCookInstructions("1 Prepare a gas or charcoal grill for medium-high, direct" +
                " heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, " +
                "cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose " +
                "paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted " +
                "into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high " +
                "heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and" +
                " heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of " +
                "arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with" +
                " the thinned sour cream. Serve with lime wedges.");
        recipes.add(spicyGrilledChickenTacosRecipe);

        log.info("Loaded: Spicy Grilled Chicken Tacos");

        return recipes;
    }

    private UnitOfMeasure getUnitOfMeasureByName(String name) {
        return unitOfMeasureRepository.findByUnit(name).get();
    }

    private Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).get();
    }


    /*Byte[] convertFileToByArray(File file) {
        Byte[] bytesArray = new Byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        return (Byte[]) bytesArray;
    }*/
}
