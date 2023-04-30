package com.example.airecipesmaker.Document;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document("recipes")
public class Recipe extends AbstractDocument {
    private String content;

    private Set<Product> products;

    private Date createdAt;

    public Recipe() {
    }

    public Recipe(String content, Set<Product> products) {
        this.content = content;
        this.products = products;
        this.createdAt = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
