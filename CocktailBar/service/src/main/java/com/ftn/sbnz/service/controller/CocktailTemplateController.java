package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.template.CocktailTemplate;
import com.ftn.sbnz.service.service.CocktailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cocktail/template")
public class CocktailTemplateController {

    @Autowired
    private CocktailTemplateService cocktailTemplateService;

    @GetMapping("/{id}")
    public CocktailTemplate getById(@PathVariable Long id) {
        return cocktailTemplateService.getById(id);
    }

    @GetMapping
    public Page<CocktailTemplate> getAll(Pageable pageable) {
        return cocktailTemplateService.getAll(pageable);
    }

    @PostMapping
    public CocktailTemplate create(@RequestBody CocktailTemplate.CocktailTemplateDTO cocktailTemplate) {
        return cocktailTemplateService.create(cocktailTemplate);
    }

    @PutMapping("/{id}")
    public CocktailTemplate update(@PathVariable Long id, @RequestBody CocktailTemplate.CocktailTemplateDTO updatedCocktail) {
        return cocktailTemplateService.update(id, updatedCocktail);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cocktailTemplateService.delete(id);
    }
}
