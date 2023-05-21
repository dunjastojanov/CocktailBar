package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.service.service.IngredientService;
import com.ftn.sbnz.service.service.IngredientUpsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientUpsertService ingredientUpsertService;

    @GetMapping("/{id}")
    public Ingredient getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @GetMapping
    public Page<Ingredient> getAll(Pageable pageable, @RequestParam(required = false) String name) {
        return ingredientService.getAll(pageable, name);
    }

    @PostMapping
    public Ingredient create(@RequestBody Ingredient.IngredientDTO ingredient) {
        return ingredientUpsertService.create(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient update(@PathVariable Long id, @RequestBody Ingredient.IngredientDTO ingredientDTO) {
        return ingredientUpsertService.update(id, ingredientDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ingredientService.delete(id);
    }
}
