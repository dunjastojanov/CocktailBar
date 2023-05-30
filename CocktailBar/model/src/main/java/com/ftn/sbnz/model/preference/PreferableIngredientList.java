package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Ingredient;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PreferableIngredientList {
    private Set<Ingredient> ingredients = new HashSet<>();

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

}
