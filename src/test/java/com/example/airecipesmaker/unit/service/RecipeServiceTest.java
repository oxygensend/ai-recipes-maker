package com.example.airecipesmaker.unit.service;

import com.example.airecipesmaker.document.Product;
import com.example.airecipesmaker.document.Recipe;
import com.example.airecipesmaker.document.util.Unit;
import com.example.airecipesmaker.dto.request.RecipeRequestDTO;
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
    Set<Product> products =  new HashSet<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeService(chatCompletion, recipeRepository);

        products.add(new Product("flour", 1, Unit.KG));
        products.add(new Product("banana", 6, Unit.QUANTITY));
        products.add(new Product("sugar", 1, Unit.KG));
        recipeList.add(new Recipe("recipe 1", products));
        recipeList.add(new Recipe("recipe 2", products));
        recipeList.add(new Recipe("recipe 3", products));
    }


    @Test
    void testCreateRecipe() throws CannotGenerateRecipeException {
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setProducts(products);

        Recipe expectedRecipe = new Recipe("recipe text", products);

        // mocks
        when(chatCompletion.generateRecipeQuestion(any(), anyInt())).thenReturn("Recipe text");
        when(recipeRepository.insert(any(Recipe.class))).thenReturn(expectedRecipe);

        Recipe recivedRecipe = recipeService.createRecipe(recipeRequestDTO);

        // verify mocked methods were called
        verify(chatCompletion, times(1)).generateRecipeQuestion(any(), anyInt());
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
    public void testGetOneRecipe() {
        String recipeId = "recipeId1";
        Recipe expectedRecipe = recipeList.get(0);
        Optional<Recipe> optionalRecipe = Optional.of(expectedRecipe);
        when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);

        Recipe result = recipeService.getOneRecipe(recipeId);

        assertEquals(expectedRecipe, result);
    }

    @Test
    public void testGetOneRecipeNotFound() {
        String recipeId = "recipeId1";
        Optional<Recipe> optionalRecipe = Optional.empty();
        when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);

        assertThrows(DocumentNotFoundException.class, () -> recipeService.getOneRecipe(recipeId));
    }

    @Test
    public void  testCreateFewRecipePropositions() {
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setProducts(products);
        recipeRequestDTO.setInstances(3);

        List<String> propositions = Arrays.asList("recipe1", "recipe2", "recipe3");

        CreateFewRecipesResponseDTO expectedResponse = new CreateFewRecipesResponseDTO(propositions, products);

        // mocks
        when(chatCompletion.generateRecipeQuestion(any(), anyInt())).thenReturn("{\"przepis_1\": \"recipe1\", \"przepis_2\": \"recipe2\", \"przepis_3\": \"recipe3\"}");
        when(recipeRepository.insert(anyList())).thenReturn(recipeList);

        CreateFewRecipesResponseDTO responseDTO = recipeService.createFewRecipes(recipeRequestDTO);

        // verify mocked methods were called
        verify(chatCompletion, times(1)).generateRecipeQuestion(any(), anyInt());
        verify(recipeRepository, times(3)).insert(any(Recipe.class));

        assertEquals(responseDTO.getPropositions(), expectedResponse.getPropositions());
    }
}
