package com.example.airecipesmaker.repository;

import com.example.airecipesmaker.Document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
