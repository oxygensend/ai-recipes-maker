package com.example.airecipesmaker.repository;

import com.example.airecipesmaker.Document.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

}
