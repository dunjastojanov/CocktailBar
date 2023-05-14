package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Flavor;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.cocktail.IngredientType;
import com.ftn.sbnz.service.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class IngredientUpsertService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeService recipeService;

    private Ingredient getById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id " + id));
    }

    public Ingredient create(Ingredient.IngredientDTO ingredient) {
        return ingredientRepository.save(getIngredient(ingredient));
    }

    private Ingredient getIngredient(Ingredient.IngredientDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(dto.getName());
        ingredient.setAlcoholPercentage(dto.getAlcoholPercentage());
        ingredient.setFlavor(Flavor.valueOf(dto.getFlavor()));
        ingredient.setType(IngredientType.valueOf(dto.getType()));
        ingredient.setRecipe(recipeService.getById(dto.getRecipeId()));
        return ingredient;
    }

    public Ingredient update(Long id, Ingredient.IngredientDTO updatedIngredient) {
        Ingredient ingredient = getById(id);
        ingredient.setName(updatedIngredient.getName());
        ingredient.setAlcoholPercentage(updatedIngredient.getAlcoholPercentage());
        ingredient.setFlavor(Flavor.valueOf(updatedIngredient.getFlavor()));
        ingredient.setType(IngredientType.valueOf(updatedIngredient.getType()));
        ingredient.setRecipe(recipeService.getById(updatedIngredient.getRecipeId()));
        return ingredientRepository.save(ingredient);
    }

}
