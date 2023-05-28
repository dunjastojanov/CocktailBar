package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDisplay {
    private List<RecipeIngredient.RecipeIngredientDisplay> ingredients = new ArrayList<>();
    private List<Cocktail.CocktailDisplayDTO> cocktails = new ArrayList<>();


    public EventDisplay(Set<Cocktail> menu, List<RecipeIngredient.RecipeIngredientDisplay> ingredients) {
        this.ingredients = ingredients;
        this.cocktails = menu.stream().map(Cocktail.CocktailDisplayDTO::new).toList();
    }
}
