package com.example.airecipesmaker.controller;

import com.example.airecipesmaker.dto.request.RecipeRequestDTO;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.dto.response.CreateFewRecipesResponseDTO;
import com.example.airecipesmaker.service.RecipeService;
import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipeController {

    private final RecipeService recipeService;

    RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @JsonView(RecipeView.GetAll.class)
    public List<Recipe> allAction() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    public Recipe newAction(@RequestBody RecipeRequestDTO requestDTO) {
        return recipeService.createRecipe(requestDTO);
    }

    @GetMapping("/{id}")
    @JsonView(RecipeView.GetOne.class)
    public Recipe getOneAction(@PathVariable("id") String id) {
        return recipeService.getOneRecipe(id);
    }

    @PostMapping("/few_propositions")
    public CreateFewRecipesResponseDTO createFewRecipes(@RequestBody RecipeRequestDTO requestDTO){
        return  recipeService.createFewRecipes(requestDTO);
    }
}
