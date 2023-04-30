package com.example.airecipesmaker.dto.response;

import com.example.airecipesmaker.document.Product;

import java.util.List;
import java.util.Set;

public class CreateFewRecipesResponseDTO {
    private List<String> propositions;
    private Set<Product> products;

    public CreateFewRecipesResponseDTO() {
    }

    public CreateFewRecipesResponseDTO(List<String> propositions, Set<Product> products) {
        this.propositions = propositions;
        this.products = products;
    }

    public List<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<String> propositions) {
        this.propositions = propositions;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
