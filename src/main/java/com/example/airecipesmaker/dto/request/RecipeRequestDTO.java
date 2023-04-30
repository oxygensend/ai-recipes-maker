package com.example.airecipesmaker.dto.request;

import com.example.airecipesmaker.document.Product;

import java.util.Set;

public class RecipeRequestDTO {
    private Set<Product> products;

    private int instances = 1;


    public RecipeRequestDTO() {
    }

    public RecipeRequestDTO(Set<Product> products) {
        this.products = products;
    }

    public Set<Product> getProducts() {
        return products;
    }


    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }
}
