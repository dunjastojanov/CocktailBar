package com.ftn.sbnz.model.cocktail;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private double amount;

    public Long getIngredientId() {
        return ingredient.getId();
    }

    @Data
    public static class RecipeIngredientDTO {
        private Long ingredientId;
        private double amount;

        public RecipeIngredientDTO(RecipeIngredient recipeIngredient) {
            ingredientId = recipeIngredient.getIngredientId();
            amount = recipeIngredient.getAmount();
        }

    }
}
