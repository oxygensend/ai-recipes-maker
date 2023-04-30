package com.example.airecipesmaker.repository;

import com.example.airecipesmaker.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
