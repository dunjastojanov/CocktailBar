package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventIngredientList {
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public boolean contains(Ingredient ingredient) {
        return ingredients.stream()
                .map(RecipeIngredient::getIngredient)
                .anyMatch(ing -> ing.equals(ingredient));
    }

    public void add(RecipeIngredient recipeIngredient) {
        ingredients.add(recipeIngredient);
    }

    public RecipeIngredient getRecipeIngredient(RecipeIngredient recipeIngredient) {

        for (RecipeIngredient existing: ingredients) {
            if (existing.getIngredient() == recipeIngredient.getIngredient())
                return existing;
        }

        return null;
    }

    public RecipeIngredient getRecipeIngredient(Ingredient ingredient) {

        for (RecipeIngredient recipeIngredient: ingredients) {
            if (recipeIngredient.getIngredient() == ingredient)
                return recipeIngredient;
        }

        return null;


    }
}
