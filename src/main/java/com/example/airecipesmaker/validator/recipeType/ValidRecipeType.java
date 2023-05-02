package com.example.airecipesmaker.validator.recipeType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RecipeTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRecipeType {

    String message() default "Invalid recipe type. Use on of: śniadanie, obiad, kolacja, przekąska, uniwersalny";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
