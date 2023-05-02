package com.example.airecipesmaker.document;

import com.example.airecipesmaker.document.util.Unit;
import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AbstractDocument {

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
