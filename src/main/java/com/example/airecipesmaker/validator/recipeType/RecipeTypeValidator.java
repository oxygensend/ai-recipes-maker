package com.example.airecipesmaker.validator.recipeType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class RecipeTypeValidator implements ConstraintValidator<ValidRecipeType, String> {
    private static final List<String> types = List.of("śniadanie", "obiad", "kolacja", "przekąska", "uniwersalny");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return types.contains(value);
    }
}
