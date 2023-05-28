package com.ftn.sbnz.model.cocktail;

import com.ftn.sbnz.model.preference.AlcoholStrength;
import com.ftn.sbnz.model.template.CocktailTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
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

    public Cocktail() {
    }

    public Cocktail(CocktailTemplate cocktailTemplate, RecipeIngredient recipeIngredient) {
        recipe = new Recipe(cocktailTemplate.getRecipe(), recipeIngredient);
        glass = cocktailTemplate.getGlass();
        image = cocktailTemplate.getImage();
        name = recipeIngredient.getIngredientName() + " " + cocktailTemplate.getName();
    }

    public double calculateStrength() {
        return recipe.calculateStrength();
    }

    public List<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipe.getRecipeIngredients();
    }


    public boolean isMadeOfIngredients(Set<Ingredient> ingredients) {
        return recipe.isMadeOfIngredients(ingredients);
    }

    public AlcoholStrength getAlcoholStrength() {
        double strength = calculateStrength();
        if (strength < 5) return AlcoholStrength.LIGHT;
        if (strength > 5 && strength < 10) return AlcoholStrength.MEDIUM;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
