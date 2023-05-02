package com.example.airecipesmaker.document.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Unit {

    MG("mg"),
    G("g"),
    KG("kg"),
    ML("ml"),
    L("l"),
    QUANTITY("quantity");

    private final String name;

}
