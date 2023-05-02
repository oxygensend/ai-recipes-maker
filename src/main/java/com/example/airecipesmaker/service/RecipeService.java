package com.example.airecipesmaker.service;

import com.example.airecipesmaker.dto.request.CreateFewRecipesRequestDTO;
import com.example.airecipesmaker.dto.request.CreateRecipeRequestDTO;
import com.example.airecipesmaker.document.Product;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.dto.response.CreateFewRecipesResponseDTO;
import com.example.airecipesmaker.exception.DocumentNotFoundException;
import com.example.airecipesmaker.openai.ChatCompletion;
import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.example.airecipesmaker.repository.RecipeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class RecipeService {

    private final ChatCompletion chatCompletion;
    private final RecipeRepository recipeRepository;


    public Recipe createRecipe(CreateRecipeRequestDTO requestDTO) throws CannotGenerateRecipeException {

        List<String> products = requestDTO.getProducts().stream().map(Product::toString).toList();
        String generatedRecipe = chatCompletion.generateRecipeQuestion(products, 1, requestDTO.getType());

        return recipeRepository.insert(
                Recipe.builder()
                        .products(requestDTO.getProducts())
                        .content(generatedRecipe)
                        .type(requestDTO.getType())
                        .build()
        );
    }

    @SuppressWarnings(value = "unchecked")
    public CreateFewRecipesResponseDTO createFewRecipes(CreateFewRecipesRequestDTO requestDTO) throws CannotGenerateRecipeException {

        List<String> products = requestDTO.getProducts().stream().map(Product::toString).toList();
        String generatedResponse = chatCompletion.generateRecipeQuestion(products, requestDTO.getInstances(), requestDTO.getType());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> recipesMap = mapper.readValue(generatedResponse, Map.class);

            for (Map.Entry<String, String> entry : recipesMap.entrySet()) {
                recipeRepository.insert(
                        Recipe.builder()
                                .products(requestDTO.getProducts())
                                .content(entry.getValue())
                                .type(requestDTO.getType())
                                .build()
                );

            }

            return CreateFewRecipesResponseDTO
                    .builder()
                    .propositions(recipesMap.values().stream().toList())
                    .products(requestDTO.getProducts())
                    .type(requestDTO.getType())
                    .build();
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

    public void deleteRecipe(String id) {
        if (recipeRepository.findById(id).isPresent()) {
            recipeRepository.deleteById(id);
        } else {
            throw new DocumentNotFoundException("No recipe found with id " + id);
        }
    }
}
