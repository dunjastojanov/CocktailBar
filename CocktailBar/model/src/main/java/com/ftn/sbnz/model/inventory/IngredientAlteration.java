package com.ftn.sbnz.model.inventory;

import com.ftn.sbnz.model.cocktail.Ingredient;
import lombok.Data;

@Data
public class IngredientAlteration {
    private Ingredient ingredient;
    private double amount;
}
