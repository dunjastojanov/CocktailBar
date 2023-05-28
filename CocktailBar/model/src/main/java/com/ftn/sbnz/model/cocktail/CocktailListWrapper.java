package com.ftn.sbnz.model.cocktail;

import com.ftn.sbnz.model.preference.AlcoholStrength;

import java.util.List;

public class CocktailListWrapper {
    private List<Cocktail> cocktails;
    private int lightCocktailsNumber;
    private int mediumCocktailsNumber;
    private int strongCocktailsNumber;

    public CocktailListWrapper() {
    }

    public CocktailListWrapper(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
        this.strongCocktailsNumber = 0;
        this.mediumCocktailsNumber = 0;
        this.lightCocktailsNumber = 0;
    }

    public List<Cocktail> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }

    public void cocktailNumWithAlcoholStrength() {
        this.lightCocktailsNumber = (int) (this.cocktails.stream().filter(cocktail -> cocktail.getAlcoholStrength().equals(AlcoholStrength.LIGHT)).count() / this.cocktails.size() * 10);
        this.mediumCocktailsNumber = (int) (this.cocktails.stream().filter(cocktail -> cocktail.getAlcoholStrength().equals(AlcoholStrength.MEDIUM)).count() / this.cocktails.size() * 10);
        this.strongCocktailsNumber = 10 - lightCocktailsNumber - mediumCocktailsNumber;
    }

    public int getLightCocktailsNumber() {
        return lightCocktailsNumber;
    }

    public int getMediumCocktailsNumber() {
        return mediumCocktailsNumber;
    }

    public int getStrongCocktailsNumber() {
        return strongCocktailsNumber;
    }


}
