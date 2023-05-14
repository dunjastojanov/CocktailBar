package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cocktail.Recipe;
import com.ftn.sbnz.service.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    public Recipe getById(@PathVariable Long id) {
        return recipeService.getById(id);
    }

    @GetMapping
    public Page<Recipe> getAll(Pageable pageable) {
        return recipeService.getAll(pageable);
    }

    @PostMapping
    public Recipe create(@RequestBody Recipe.RecipeDTO recipe) {
        return recipeService.create(recipe);
    }

    @PutMapping("/{id}")
    public Recipe update(@PathVariable Long id, @RequestBody Recipe.RecipeDTO recipeDTO) {
        return recipeService.update(id, recipeDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }
}
