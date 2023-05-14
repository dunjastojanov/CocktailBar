package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Recipe;
import com.ftn.sbnz.service.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeIngredientService recipeIngredientService;


    public Recipe getById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found with id " + id));
    }

    public Page<Recipe> getAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe create(Recipe.RecipeDTO recipe) {
        return recipeRepository.save(getRecipe(recipe));
    }

    private Recipe getRecipe(Recipe.RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setRecipeIngredients(dto.getRecipeIngredients().stream().map(recipeIngredientId -> recipeIngredientService.getById(recipeIngredientId)).collect(Collectors.toSet()));
        recipe.setInstructions(dto.getInstructions());
        return recipe;
    }

    public Recipe update(Long id, Recipe.RecipeDTO updatedRecipe) {
        Recipe recipe = getById(id);
        recipe.setRecipeIngredients(updatedRecipe.getRecipeIngredients().stream().map(recipeIngredientId -> recipeIngredientService.getById(recipeIngredientId)).collect(Collectors.toSet()));
        recipe.setInstructions(updatedRecipe.getInstructions());
        return recipeRepository.save(recipe);
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
