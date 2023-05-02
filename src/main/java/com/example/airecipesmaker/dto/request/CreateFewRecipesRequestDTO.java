package com.example.airecipesmaker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFewRecipesRequestDTO extends CreateRecipeRequestDTO {

    private int instances;

}
