package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.exception.ObjectNotFoundException;
import guru.springframework.spring5recipeapp.service.CategoryService;
import guru.springframework.spring5recipeapp.service.ImageService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    public static final String VIEWS_400 = "400";
    public static final String VIEWS_404 = "404";
    public static final String VIEWS_RECIPES_DETAIL = "recipes/detail";
    public static final String VIEWS_RECIPES_FORM = "recipes/form";
    public static final String VIEWS_RECIPES_IMAGES_FORM = "recipes/images/form";
    public static final String VIEWS_RECIPES_REDIRECT = "redirect:/recipes/";
    public static final String VIEWS_ROOT_REDIRECT = "redirect:/";
    public static final String RECIPE_ATTRIBUTE_NAME = "recipe";

    private final CategoryService categoryService;
    private final RecipeService recipeService;
    private final ImageService imageService;

    @Autowired
    public RecipeController(CategoryService categoryService, RecipeService recipeService, ImageService imageService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/{recipeId}")
    public String showRecipeDetail(@PathVariable String recipeId, Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE_NAME, recipeService.findById(recipeId));

        return VIEWS_RECIPES_DETAIL;
    }

    @GetMapping({"/edit", "/edit/{recipeId}"})
    public String showRecipeForm(@PathVariable(required = false) String recipeId, Model model) {
        if (recipeId != null) {
            model.addAttribute(RECIPE_ATTRIBUTE_NAME, recipeService.findById(recipeId).block());
        } else {
            model.addAttribute(RECIPE_ATTRIBUTE_NAME, new RecipeDTO());
        }
        model.addAttribute("categories", categoryService.findAll());

        return VIEWS_RECIPES_FORM;
    }

    @PostMapping("")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeDTO recipe, BindingResult bindingResult) {   // Add @Valid to the Modelattribute parameter to trigger Bean Validation. Make sure to add the model attribute name if the parameter type name doesn't match the object name in the form in the th:object Thymeleaf attribute. Add BindingResult parameters which will contains the validation result.

        // Check validation result, log any validation errors and in that case return to the recipe form
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return VIEWS_RECIPES_FORM;
        }

        RecipeDTO savedRecipeDTO = recipeService.save(recipe).block();

        return VIEWS_RECIPES_REDIRECT + savedRecipeDTO.getId(); // Use MVC redirect to redirect user to the Recipe Detail page (so the browser should load a new url here)
    }

    @GetMapping("/{recipeId}/delete")
    public String deleteById(@PathVariable String recipeId) {
        recipeService.deleteById(recipeId);

        return VIEWS_ROOT_REDIRECT;
    }

    @GetMapping("/{recipeId}/image/edit")
    public String showImageUploadForm(@PathVariable String recipeId, Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE_NAME, recipeService.findById(recipeId).block());

        return VIEWS_RECIPES_IMAGES_FORM;
    }

    @PostMapping("/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile imageFile) {
        imageService.saveOnRecipe(recipeId, imageFile);

        return VIEWS_RECIPES_REDIRECT + recipeId;
    }

/*    @GetMapping("/{recipeId}/image")
    public void showImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeDTO recipeDTO = recipeService.findById(recipeId).block();
        byte[] bytes = ArrayUtils.toPrimitive(recipeDTO.getImage());    // Convert Byte[] to byte[]

        if (bytes != null) {
            InputStream is = new ByteArrayInputStream(bytes);               // copy the bytes to the response outputstream
            response.setContentType("image/jpeg");
            IOUtils.copy(is, response.getOutputStream());
        }
    }*/

/*    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleObjectNotFoundException(Exception exception) {
        log.warn("Handling ObjectNotFoundException");
        log.warn(exception.getMessage());
        ModelAndView mav = new ModelAndView("404");
        mav.addObject("exception", exception);

        return mav;
    }*/
}
