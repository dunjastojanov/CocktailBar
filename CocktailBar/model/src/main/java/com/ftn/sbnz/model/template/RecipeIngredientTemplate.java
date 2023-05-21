package com.ftn.sbnz.model.template;

import com.ftn.sbnz.model.cocktail.IngredientType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RecipeIngredientTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private IngredientType type;
    private double amount;

    @Data
    public static class RecipeIngredientTemplateDTO {
        private String type;
        private double amount;

        public RecipeIngredientTemplateDTO(RecipeIngredientTemplate recipeIngredient) {
            type = recipeIngredient.getType().toString();
            amount = recipeIngredient.getAmount();
        }

    }
}
