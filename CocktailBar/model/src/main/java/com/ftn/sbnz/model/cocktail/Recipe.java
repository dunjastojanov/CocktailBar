package com.ftn.sbnz.model.cocktail;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @Column(length = 65535)
    private String instructions;

    public double calculateStrength() {
        double totalLiquid = recipeIngredients.stream().map(RecipeIngredient::getAmount).reduce(0.0, Double::sum);
        double totalAlcohol = recipeIngredients.stream()
                .mapToDouble(recipeIngredient -> recipeIngredient.getAmount() * recipeIngredient.getIngredient().getAlcoholPercentage() / 100.0)
                .sum();

        return totalAlcohol/totalLiquid;
    }

    public List<Ingredient> getIngredients() {
        return recipeIngredients.stream().map(RecipeIngredient::getIngredient).toList();
    }

    @Data
    public static class RecipeDTO {
        private List<Long> recipeIngredients;
        private String instructions;

        public RecipeDTO(Recipe recipe) {
            recipeIngredients = recipe.getRecipeIngredients().stream().map(RecipeIngredient::getId).toList();
            instructions = recipe.getInstructions();
        }

    }
}
