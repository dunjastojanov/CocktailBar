package com.ftn.sbnz.model.cocktail;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    @Data
    public class IngredientDTO {
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
            recipeId = ingredient.getRecipeId();
        }
    }


}
