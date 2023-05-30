package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Cocktail;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PreferableCocktailList {
    private Set<Cocktail> cocktails = new HashSet<>();

    public void addCocktail(Cocktail cocktail) {
        cocktails.add(cocktail);
    }

    public boolean containsCocktail(Cocktail cocktail) {
        return cocktails.contains(cocktail);
    }

}
