package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class IngredientController {

    public static final String VIEWS_RECIPES_INGREDIENTS_FORM = "recipes/ingredients/form";

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    // Manually get a handle on the binding framework within Spring
    private WebDataBinder webDataBinder;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @Autowired
    public IngredientController(IngredientService ingredientService, RecipeService recipeService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String showListForRecipe(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(recipeId));

        return "recipes/ingredients/list";
    }

    @GetMapping("/recipes/{recipeId}/ingredients/new")
    public String showNewIngredientFormForRecipe(@PathVariable String recipeId, Model model) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);
        ingredientDTO.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientDTO);

        return VIEWS_RECIPES_INGREDIENTS_FORM;
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public String showIngredientForRecipe(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findById(recipeId, ingredientId));

        return VIEWS_RECIPES_INGREDIENTS_FORM;
    }

    // Do NOT forget to add the model parameter for setting the uomList for the form in case of a validation error
    @PostMapping("/recipes/{recipeId}/ingredients")
    public String save(@PathVariable String recipeId, @ModelAttribute("ingredient") IngredientDTO ingredientDTO, Model model) {              // The incoming uomDTO will only contain an id from the form select box here

        // Manually perform the validation and bind the result, so you can check for validation errors
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        // Check validation result, log any validation errors and in that case return to the recipe form
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return VIEWS_RECIPES_INGREDIENTS_FORM;
        }

        IngredientDTO savedIngredientDTO = ingredientService.saveIngredientOnRecipe(recipeId, ingredientDTO).block();

        return "redirect:/recipes/" + recipeId + "/ingredients/" + savedIngredientDTO.getId();
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}/delete")
    public String deleteIngredientFromRecipe(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(ingredientId).block();

        return "redirect:/recipes/" + recipeId + "/ingredients";
    }

    // Every time we return a model to the view layer we will populate it with an attribute uomList via unitOfMeasureService
    @ModelAttribute("uomList")          // uomList is the property name it is going to bind the output of this method to
    public Flux<UnitOfMeasureDTO> populateUomList() {
        return  unitOfMeasureService.findAll();
    }
}
