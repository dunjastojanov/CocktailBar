package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.service.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cocktail")
public class CocktailController {

    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/{id}")
    public Cocktail getById(@PathVariable Long id) {
        return cocktailService.getById(id);
    }

    @GetMapping
    public Page<Cocktail> getAll(Pageable pageable, @RequestParam(required = false) String name) {
        return cocktailService.getAll(pageable, name);
    }

    @PostMapping
    public Cocktail create(@RequestBody Cocktail.CocktailDTO cocktail) {
        return cocktailService.create(cocktail);
    }

    @PutMapping("/{id}")
    public Cocktail update(@PathVariable Long id, @RequestBody Cocktail.CocktailDTO updatedCocktail) {
        return cocktailService.update(id, updatedCocktail);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cocktailService.delete(id);
    }
}
