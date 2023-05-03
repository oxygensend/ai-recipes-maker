package com.example.airecipesmaker.unit.service;

import com.example.airecipesmaker.document.util.Product;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.document.util.Unit;
import com.example.airecipesmaker.dto.request.CreateFewRecipesRequestDTO;
import com.example.airecipesmaker.dto.request.CreateRecipeRequestDTO;
import com.example.airecipesmaker.dto.response.CreateFewRecipesResponseDTO;
import com.example.airecipesmaker.exception.CannotGenerateRecipeException;
import com.example.airecipesmaker.exception.DocumentNotFoundException;
import com.example.airecipesmaker.openai.ChatCompletion;
import com.example.airecipesmaker.repository.RecipeRepository;
import com.example.airecipesmaker.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    ChatCompletion chatCompletion;

    @Mock
    RecipeRepository recipeRepository;

    RecipeService recipeService;

    List<Recipe> recipeList = new ArrayList<>();
    Set<Product> products = new HashSet<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeService(chatCompletion, recipeRepository);

        products.add(new Product("flour", 1, Unit.KG));
        products.add(new Product("banana", 6, Unit.QUANTITY));
        products.add(new Product("sugar", 1, Unit.KG));
        recipeList.add(Recipe.builder().content("recipe 1").products(products).type("uniwersalny").build());
        recipeList.add(Recipe.builder().content("recipe 2").products(products).type("uniwersalny").build());
        recipeList.add(Recipe.builder().content("recipe 3").products(products).type("uniwersalny").build());
    }


    @Test
    void testCreateRecipe() throws CannotGenerateRecipeException {
        CreateRecipeRequestDTO recipeRequestDTO = new CreateRecipeRequestDTO();
        recipeRequestDTO.setProducts(products);

        Recipe expectedRecipe = Recipe.builder().content("recipe text").products(products).type("uniwersalny").build();

        // mocks
        when(chatCompletion.generateRecipeQuestion(any(), anyInt(), any())).thenReturn("Recipe text");
        when(recipeRepository.insert(any(Recipe.class))).thenReturn(expectedRecipe);

        Recipe recivedRecipe = recipeService.createRecipe(recipeRequestDTO);

        // verify mocked methods were called
        verify(chatCompletion, times(1)).generateRecipeQuestion(any(), anyInt(), any());
        verify(recipeRepository, times(1)).insert(any(Recipe.class));

        assertEquals(recivedRecipe, expectedRecipe);
    }


    @Test
    public void testGetAllRecipes() {

        when(recipeRepository.findAll()).thenReturn(recipeList);

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(recipeList, result);
    }
    @Test
    public void testGetOneRecipeNotFound() {
        String recipeId = "recipeId1";
        Optional<Recipe> optionalRecipe = Optional.empty();
        when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);

        assertThrows(DocumentNotFoundException.class, () -> recipeService.getOneRecipe(recipeId));
    }

    @Test
    public void testCreateFewRecipePropositions() {
        CreateFewRecipesRequestDTO recipeRequestDTO = new CreateFewRecipesRequestDTO();
        recipeRequestDTO.setProducts(products);
        recipeRequestDTO.setInstances(3);

        List<String> propositions = Arrays.asList("recipe1", "recipe2", "recipe3");

        CreateFewRecipesResponseDTO expectedResponse = CreateFewRecipesResponseDTO.builder()
                .products(products)
                .propositions(propositions)
                .type("uniwersalny")
                .build();

        // mocks
        when(chatCompletion.generateRecipeQuestion(any(), anyInt(), any())).thenReturn("{\"przepis_1\": \"recipe1\", \"przepis_2\": \"recipe2\", \"przepis_3\": \"recipe3\"}");
        when(recipeRepository.insert(anyList())).thenReturn(recipeList);

        CreateFewRecipesResponseDTO responseDTO = recipeService.createFewRecipes(recipeRequestDTO);

        // verify mocked methods were called
        verify(chatCompletion, times(1)).generateRecipeQuestion(any(), anyInt(), any());
        verify(recipeRepository, times(3)).insert(any(Recipe.class));

        assertEquals(responseDTO.getPropositions(), expectedResponse.getPropositions());
    }

    @Test
    public void testDeleteRecipeWithValidId() {
        // Arrange
        String recipeId = "123";
        Recipe recipe = Recipe.builder().content("recipe text").products(products).type("uniwersalny").build();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        recipeService.deleteRecipe(recipeId);
        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    public void testDeleteRecipeWithInvalidId() {

        String recipeId = "123";
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(DocumentNotFoundException.class, () -> {
            recipeService.deleteRecipe(recipeId);
        });
        verify(recipeRepository, never()).deleteById(anyString());
    }
}
