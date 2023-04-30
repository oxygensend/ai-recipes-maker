package com.example.airecipesmaker.document;

import com.example.airecipesmaker.document.util.Unit;
import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
public class Product extends AbstractDocument {

    @JsonView(RecipeView.GetOne.class)
    private String name;
    @JsonView(RecipeView.GetOne.class)
    private double quantity;
    @JsonView(RecipeView.GetOne.class)
    private Unit unit;

    public Product() {
    }

    public Product(String name, double quantity, Unit unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.name + " -> " + this.quantity + " " + this.unit.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
