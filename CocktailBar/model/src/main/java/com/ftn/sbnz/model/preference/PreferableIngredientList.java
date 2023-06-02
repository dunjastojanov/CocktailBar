package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferableIngredientList {
    private Set<Ingredient> ingredients;

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    public void addIngredient(Ingredient ingredient){
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        ingredients.add(ingredient);
    }

}
