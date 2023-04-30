package com.example.airecipesmaker.document;


import org.springframework.data.annotation.Id;

public class AbstractDocument {

    @Id
    protected String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
