package com.example.airecipesmaker.repository;

import com.example.airecipesmaker.document.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

}
