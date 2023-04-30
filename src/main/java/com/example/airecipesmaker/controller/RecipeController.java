package com.example.airecipesmaker.controller;

import com.example.airecipesmaker.dto.RecipeRequestDTO;
import com.example.airecipesmaker.Document.Recipe;
import com.example.airecipesmaker.service.RecipeService;
import org.springframework.web.bind.annotation.*;
import com.example.airecipesmaker.repository.RecipeRepository;

import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    RecipeController(RecipeRepository historyRepository, RecipeService recipeService) {
        this.recipeRepository = historyRepository;
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> allAction() {
        return recipeRepository.findAll();
    }

    @PostMapping
    public Recipe newAction(@RequestBody RecipeRequestDTO requestDTO) {
        return recipeService.createRecipe(requestDTO);
    }
}
