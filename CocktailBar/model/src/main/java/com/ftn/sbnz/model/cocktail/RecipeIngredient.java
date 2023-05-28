package com.ftn.sbnz.model.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private double amount;

    public RecipeIngredient(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
    public Long getIngredientId() {
        return ingredient.getId();
    }
    public String getIngredientName() {
        return ingredient.getName();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    @Data

    public static class RecipeIngredientDisplay {
        private String ingredient;
        private double amount;

        public RecipeIngredientDisplay() {
        }

        public RecipeIngredientDisplay(String ingredient, double amount) {
            this.ingredient = ingredient;
            this.amount = amount;
        }

        public RecipeIngredientDisplay(RecipeIngredient recipeIngredient, double amount) {
            this.ingredient = recipeIngredient.getIngredient().getName();
            this.amount = amount;
        }

        public RecipeIngredientDisplay(RecipeIngredient recipeIngredient) {
            ingredient = recipeIngredient.getIngredient().getName();
            amount = recipeIngredient.getAmount();
        }

    }
}
