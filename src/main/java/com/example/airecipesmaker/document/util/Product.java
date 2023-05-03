package com.example.airecipesmaker.document.util;

import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @NotBlank
    @JsonView(RecipeView.GetOne.class)
    private String name;

    @JsonView(RecipeView.GetOne.class)
    private double quantity;

    @JsonView(RecipeView.GetOne.class)
    private Unit unit;

    @Override
    public String toString() {
        return this.name + " -> " + this.quantity + " " + this.unit.getName();
    }

}
