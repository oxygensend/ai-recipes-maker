package com.example.airecipesmaker.service;

import com.example.airecipesmaker.dto.RecipeRequestDTO;
import com.example.airecipesmaker.Document.Product;
import com.example.airecipesmaker.Document.Recipe;
import com.example.airecipesmaker.openai.ChatCompletion;
import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.example.airecipesmaker.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RecipeService {

    private final ChatCompletion chatCompletion;
    private final RecipeRepository recipeRepository;

    public RecipeService(ChatCompletion chatCompletion, RecipeRepository recipeRepository) {
        this.chatCompletion = chatCompletion;
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(RecipeRequestDTO requestDTO) throws CannotGenerateRecipeException {

        List<String> products = requestDTO.getProducts().stream().map(Product::toString).toList();
        String generatedRecipe = this.chatCompletion.generateRecipeQuestion(products);
        return recipeRepository.insert(new Recipe(generatedRecipe, requestDTO.getProducts()));
    }
}
