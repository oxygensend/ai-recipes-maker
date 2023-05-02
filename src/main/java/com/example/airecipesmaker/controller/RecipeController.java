package com.example.airecipesmaker.controller;

import com.example.airecipesmaker.dto.request.CreateFewRecipesRequestDTO;
import com.example.airecipesmaker.dto.request.CreateRecipeRequestDTO;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.dto.response.CreateFewRecipesResponseDTO;
import com.example.airecipesmaker.service.RecipeService;
import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    @JsonView(RecipeView.GetAll.class)
    public List<Recipe> allAction() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    public Recipe newAction(@RequestBody @Validated CreateRecipeRequestDTO requestDTO) {
        return recipeService.createRecipe(requestDTO);
    }

    @GetMapping("/{id}")
    @JsonView(RecipeView.GetOne.class)
    public Recipe getOneAction(@PathVariable("id") String id) {
        return recipeService.getOneRecipe(id);
    }

    @PostMapping("/few_propositions")
    public CreateFewRecipesResponseDTO createFewRecipes(@RequestBody @Validated CreateFewRecipesRequestDTO requestDTO){
        return  recipeService.createFewRecipes(requestDTO);
    }
}
