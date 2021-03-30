package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(IngredientService ingredientService, RecipeService recipeService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String showListForRecipe(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(recipeId).block());

        return "recipes/ingredients/list";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/new")
    public String showNewIngredientFormForRecipe(@PathVariable String recipeId, Model model) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);
        ingredientDTO.setRecipe(recipeDTO);
        model.addAttribute("ingredient", ingredientDTO);
        model.addAttribute("uomList", unitOfMeasureService.findAll().collectList().block());

        return "recipes/ingredients/form";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public String showIngredientForRecipe(@PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findById(ingredientId).block());
        model.addAttribute("uomList", unitOfMeasureService.findAll().collectList().block());

        return "recipes/ingredients/form";
    }

    @PostMapping("/recipes/{recipeId}/ingredients")
    public String save(@PathVariable String recipeId, @ModelAttribute IngredientDTO ingredientDTO) {              // The incoming uomDTO will only contain an id from the form select box here
        IngredientDTO savedIngredientDTO = ingredientService.saveIngredientOnRecipe(recipeId, ingredientDTO).block();

        return "redirect:/recipes/" + recipeId + "/ingredients/" + savedIngredientDTO.getId();
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}/delete")
    public String deleteIngredientFromRecipe(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(ingredientId).block();

        return "redirect:/recipes/" + recipeId + "/ingredients";
    }
}
