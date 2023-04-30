package com.example.airecipesmaker.unit.service;

import com.example.airecipesmaker.document.Product;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.document.util.Unit;
import com.example.airecipesmaker.dto.RecipeRequestDTO;
import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.example.airecipesmaker.openai.ChatCompletion;
import com.example.airecipesmaker.repository.RecipeRepository;
import com.example.airecipesmaker.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    ChatCompletion chatCompletion;

    @Mock
    RecipeRepository recipeRepository;

    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeService(chatCompletion, recipeRepository);
    }

    @Test
    void testCreateRecipe() throws CannotGenerateRecipeException {
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();
        Set<Product> products = new HashSet<>();
        products.add(new Product("flour", 1, Unit.KG));
        products.add(new Product("banana", 6, Unit.QUANTITY));
        products.add(new Product("sugar", 1, Unit.KG));

        recipeRequestDTO.setProducts(products);

        Recipe expectedRecipe = new Recipe("recipe text", products);

        // mocks
        when(chatCompletion.generateRecipeQuestion(any())).thenReturn("Recipe text");
        when(recipeRepository.insert(any(Recipe.class))).thenReturn(expectedRecipe);

        Recipe recivedRecipe = recipeService.createRecipe(recipeRequestDTO);

        // verify mocked methods were called
        verify(chatCompletion, times(1)).generateRecipeQuestion(any());
        verify(recipeRepository, times(1)).insert(any(Recipe.class));

        assertEquals(recivedRecipe, expectedRecipe);
    }

}
