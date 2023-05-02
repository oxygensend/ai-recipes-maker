package com.example.airecipesmaker.document;


import com.example.airecipesmaker.views.DefaultView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class AbstractDocument {

    @Id
    @JsonView(DefaultView.Id.class)
    protected String id;

}
