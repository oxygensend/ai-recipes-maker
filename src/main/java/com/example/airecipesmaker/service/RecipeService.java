package com.example.airecipesmaker.service;

import com.example.airecipesmaker.dto.request.RecipeRequestDTO;
import com.example.airecipesmaker.document.Product;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.dto.response.CreateFewRecipesResponseDTO;
import com.example.airecipesmaker.exception.DocumentNotFoundException;
import com.example.airecipesmaker.openai.ChatCompletion;
import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.example.airecipesmaker.repository.RecipeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        String generatedRecipe = chatCompletion.generateRecipeQuestion(products, requestDTO.getInstances());
        return recipeRepository.insert(new Recipe(generatedRecipe, requestDTO.getProducts()));
    }

    @SuppressWarnings(value = "unchecked")
    public CreateFewRecipesResponseDTO createFewRecipes(RecipeRequestDTO requestDTO) throws CannotGenerateRecipeException {

        List<String> products = requestDTO.getProducts().stream().map(Product::toString).toList();
        String generatedResponse = chatCompletion.generateRecipeQuestion(products, requestDTO.getInstances());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> recipesMap = mapper.readValue(generatedResponse, Map.class);

            for (Map.Entry<String, String> entry : recipesMap.entrySet()) {
                recipeRepository.insert(new Recipe(entry.getValue(), requestDTO.getProducts()));
            }

            return new CreateFewRecipesResponseDTO(recipesMap.values().stream().toList(), requestDTO.getProducts());
        } catch (JsonProcessingException e) {
            throw new CannotGenerateRecipeException(e.getMessage());
        }

    }


    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getOneRecipe(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.orElseThrow(() -> new DocumentNotFoundException("No recipe found with id " + id));
    }
}
