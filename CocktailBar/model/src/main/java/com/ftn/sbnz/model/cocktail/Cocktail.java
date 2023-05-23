package com.ftn.sbnz.model.cocktail;

import com.ftn.sbnz.model.preference.AlcoholStrength;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Glass glass;
    private String image;
    @OneToOne
    private Recipe recipe;

    public double calculateStrength() {
        return recipe.calculateStrength();
    }

    public boolean isAlcoholStrength(AlcoholStrength alcoholStrength) {
        if (alcoholStrength == AlcoholStrength.LIGHT)
            return calculateStrength() < 5;
        else if (alcoholStrength == AlcoholStrength.MEDIUM) {
            double strength = calculateStrength();
            return strength > 5 && strength < 10;
        } else
            return calculateStrength() > 10;
    }

    public List<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipe.getRecipeIngredients();
    }

    public AlcoholStrength getAlcoholStrength() {
        double strength = calculateStrength();
        if (strength < 15) return AlcoholStrength.LIGHT;
        if (strength > 15 && strength < 35) return AlcoholStrength.MEDIUM;
        else return AlcoholStrength.STRONG;
    }

    @Data
    public static class CocktailDTO {
        String name;
        String glass;
        String image;
        Long recipeId;

        public CocktailDTO(Cocktail cocktail) {
            name = cocktail.getName();
            glass = cocktail.getGlass().toString();
            image = cocktail.getImage();
            recipeId = cocktail.getRecipe().getId();
        }

    }

    @Data
    public static class CocktailDisplayDTO {
        String name;
        String glass;
        String image;
        AlcoholStrength alcoholStrength;
        List<Ingredient.IngredientDisplayDTO> ingredients = new ArrayList<>();

        public CocktailDisplayDTO(Cocktail cocktail) {
            name = cocktail.getName();
            glass = cocktail.getGlass().toString();
            image = cocktail.getImage();
            alcoholStrength = cocktail.getAlcoholStrength();

            for (Ingredient ingredient : cocktail.getIngredients()) {
                ingredients.add(new Ingredient.IngredientDisplayDTO(ingredient));
            }
        }
    }


}
