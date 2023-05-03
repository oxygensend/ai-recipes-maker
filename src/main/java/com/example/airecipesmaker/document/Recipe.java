package com.example.airecipesmaker.document;

import com.example.airecipesmaker.document.util.Product;
import com.example.airecipesmaker.views.RecipeView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document("recipes")
@Data
@Builder
@AllArgsConstructor
public class Recipe extends AbstractDocument {


    @NonNull
    @JsonView({RecipeView.GetAll.class, RecipeView.GetOne.class})
    private String content;

    @NonNull
    @JsonView(RecipeView.GetOne.class)
    private Set<Product> products;


    @NonNull
    private String type;

    @Builder.Default
    @JsonView(RecipeView.GetOne.class)
    private Date createdAt = new Date();


}
