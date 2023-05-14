package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import com.ftn.sbnz.service.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe/ingredient")
public class RecipeIngredientController {

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @GetMapping("/{id}")
    public RecipeIngredient getById(@PathVariable Long id) {
        return recipeIngredientService.getById(id);
    }

    @GetMapping
    public Page<RecipeIngredient> getAll(Pageable pageable) {
        return recipeIngredientService.getAll(pageable);
    }

    @PostMapping
    public RecipeIngredient create(@RequestBody RecipeIngredient.RecipeIngredientDTO recipeIngredient) {
        return recipeIngredientService.create(recipeIngredient);
    }

    @PutMapping("/{id}")
    public RecipeIngredient update(@PathVariable Long id, @RequestBody RecipeIngredient.RecipeIngredientDTO updatedRecipeIngredient) {
        return recipeIngredientService.update(id, updatedRecipeIngredient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeIngredientService.delete(id);
    }
}
