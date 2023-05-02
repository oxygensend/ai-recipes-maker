package com.example.airecipesmaker.controller;

import com.example.airecipesmaker.dto.request.CreateFewRecipesRequestDTO;
import com.example.airecipesmaker.dto.request.CreateRecipeRequestDTO;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.dto.response.CreateFewRecipesResponseDTO;
import com.example.airecipesmaker.service.RecipeService;
import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonView(RecipeView.GetAll.class)
    public List<Recipe> allAction() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe newAction(@RequestBody @Validated CreateRecipeRequestDTO requestDTO) {
        return recipeService.createRecipe(requestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(RecipeView.GetOne.class)
    public Recipe getOneAction(@PathVariable("id") String id) {
        return recipeService.getOneRecipe(id);
    }

    @PostMapping("/few_propositions")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateFewRecipesResponseDTO createFewRecipes(@RequestBody @Validated CreateFewRecipesRequestDTO requestDTO){
        return  recipeService.createFewRecipes(requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable("id") String id){
        recipeService.deleteRecipe(id);
    }
}
