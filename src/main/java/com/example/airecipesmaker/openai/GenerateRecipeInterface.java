package com.example.airecipesmaker.openai;

import com.example.airecipesmaker.exception.CannotGenerateRecipeException;

import java.util.List;

public interface GenerateRecipeInterface {

    String generateRecipeQuestion(List<String> products, int instances, String type) throws CannotGenerateRecipeException;
}
