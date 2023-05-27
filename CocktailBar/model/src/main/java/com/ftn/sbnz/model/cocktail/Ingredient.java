package com.ftn.sbnz.model.cocktail;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int alcoholPercentage;
    @Enumerated(EnumType.STRING)
    private Flavor flavor;
    @Enumerated(EnumType.STRING)
    private IngredientType type;
    @OneToOne
    private Recipe recipe;

    public Long getRecipeId() {
        return recipe.getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Data
    public static class IngredientDTO {
        private String name;
        private int alcoholPercentage;
        private String flavor;
        private String type;
        private Long recipeId;

        public IngredientDTO(Ingredient ingredient) {
            name = ingredient.getName();
            alcoholPercentage = ingredient.getAlcoholPercentage();
            flavor = ingredient.getFlavor().toString();
            type = ingredient.getType().toString();
            if (ingredient.getRecipe() != null) {
                recipeId = ingredient.getRecipeId();
            }

        }
    }
    @Data
    public static class IngredientDisplayDTO {
        private String name;
        private int alcoholPercentage;
        private String flavor;
        private String type;

        public IngredientDisplayDTO(Ingredient ingredient) {
            name = ingredient.getName();
            alcoholPercentage = ingredient.getAlcoholPercentage();
            flavor = ingredient.getFlavor().toString();
            type = ingredient.getType().toString();
        }

    }
}
