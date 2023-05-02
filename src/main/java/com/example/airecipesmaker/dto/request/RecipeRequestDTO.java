package com.example.airecipesmaker.dto.request;

import com.example.airecipesmaker.document.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestDTO {

    private Set<Product> products;
    private int instances = 1;

}
