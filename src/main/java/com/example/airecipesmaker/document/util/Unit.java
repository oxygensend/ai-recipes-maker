package com.example.airecipesmaker.document.util;

public enum Unit {

    MG("mg"),
    G("g"),
    KG("kg"),
    ML("ml"),
    L("l"),
    QUANTITY("quantity");


    private final String name;

    Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
