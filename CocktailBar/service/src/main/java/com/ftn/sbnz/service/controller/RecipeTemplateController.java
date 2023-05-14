package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.template.RecipeTemplate;
import com.ftn.sbnz.service.service.RecipeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe/template")
public class RecipeTemplateController {

    @Autowired
    private RecipeTemplateService recipeTemplateService;

    @GetMapping("/{id}")
    public RecipeTemplate getById(@PathVariable Long id) {
        return recipeTemplateService.getById(id);
    }

    @GetMapping
    public Page<RecipeTemplate> getAll(Pageable pageable) {
        return recipeTemplateService.getAll(pageable);
    }

    @PostMapping
    public RecipeTemplate create(@RequestBody RecipeTemplate.RecipeTemplateDTO recipeTemplate) {
        return recipeTemplateService.create(recipeTemplate);
    }

    @PutMapping("/{id}")
    public RecipeTemplate update(@PathVariable Long id, @RequestBody RecipeTemplate.RecipeTemplateDTO recipeDTO) {
        return recipeTemplateService.update(id, recipeDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeTemplateService.delete(id);
    }
}
