package com.ftn.sbnz.model.preference;

import com.ftn.sbnz.model.cocktail.Ingredient;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PreferableIngredientList {
    private Long referenceId;
    private Set<Ingredient> ingredients = new HashSet<>();

}
