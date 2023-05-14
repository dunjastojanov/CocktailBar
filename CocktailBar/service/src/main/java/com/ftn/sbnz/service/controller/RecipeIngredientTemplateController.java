package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.template.RecipeIngredientTemplate;
import com.ftn.sbnz.service.service.RecipeIngredientTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe/ingredient/template")
public class RecipeIngredientTemplateController {

    @Autowired
    private RecipeIngredientTemplateService recipeIngredientTemplateService;

    @GetMapping("/{id}")
    public RecipeIngredientTemplate getById(@PathVariable Long id) {
        return recipeIngredientTemplateService.getById(id);
    }

    @GetMapping
    public Page<RecipeIngredientTemplate> getAll(Pageable pageable) {
        return recipeIngredientTemplateService.getAll(pageable);
    }

    @PostMapping
    public RecipeIngredientTemplate create(@RequestBody RecipeIngredientTemplate.RecipeIngredientTemplateDTO recipeIngredient) {
        return recipeIngredientTemplateService.create(recipeIngredient);
    }

    @PutMapping("/{id}")
    public RecipeIngredientTemplate update(@PathVariable Long id, @RequestBody RecipeIngredientTemplate.RecipeIngredientTemplateDTO updatedRecipeIngredient) {
        return recipeIngredientTemplateService.update(id, updatedRecipeIngredient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeIngredientTemplateService.delete(id);
    }
}
