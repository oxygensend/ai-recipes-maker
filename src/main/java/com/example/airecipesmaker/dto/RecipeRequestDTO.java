package com.example.airecipesmaker.dto;

import com.example.airecipesmaker.Document.Product;

import java.util.Set;

public class RecipeRequestDTO {
    private Set<Product> products;

    public Set<Product> getProducts() {
        return products;
    }

    public RecipeRequestDTO() {
    }

    public RecipeRequestDTO(Set<Product> products) {
        this.products = products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
