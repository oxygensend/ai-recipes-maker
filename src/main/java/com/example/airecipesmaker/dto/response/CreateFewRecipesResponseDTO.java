package com.example.airecipesmaker.dto.response;

import com.example.airecipesmaker.document.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFewRecipesResponseDTO {
    private List<String> propositions;
    private Set<Product> products;
    private String type;
}
