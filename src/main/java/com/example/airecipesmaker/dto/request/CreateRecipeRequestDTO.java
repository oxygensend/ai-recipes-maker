package com.example.airecipesmaker.dto.request;

import com.example.airecipesmaker.document.Product;
import com.example.airecipesmaker.validator.recipeType.ValidRecipeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecipeRequestDTO {

    @NotEmpty
    protected Set<@NotBlank Product> products;

    @ValidRecipeType
    protected String type = "uniwersalny";

}
