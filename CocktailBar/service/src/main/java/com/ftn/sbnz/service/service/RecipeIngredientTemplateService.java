package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.IngredientType;
import com.ftn.sbnz.model.template.RecipeIngredientTemplate;
import com.ftn.sbnz.service.repository.RecipeIngredientTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RecipeIngredientTemplateService {

    @Autowired
    private RecipeIngredientTemplateRepository recipeIngredientTemplateRepository;
    

    public RecipeIngredientTemplate getById(Long id) {
        return recipeIngredientTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe ingredient template not found with id " + id));
    }

    public Page<RecipeIngredientTemplate> getAll(Pageable pageable) {
        return recipeIngredientTemplateRepository.findAll(pageable);
    }

    public RecipeIngredientTemplate create(RecipeIngredientTemplate.RecipeIngredientTemplateDTO recipeIngredient) {
        return recipeIngredientTemplateRepository.save(getRecipeIngredient(recipeIngredient));
    }

    private RecipeIngredientTemplate getRecipeIngredient(RecipeIngredientTemplate.RecipeIngredientTemplateDTO dto) {
        RecipeIngredientTemplate recipeIngredient = new RecipeIngredientTemplate();
        recipeIngredient.setAmount(dto.getAmount());
        recipeIngredient.setType(IngredientType.valueOf(dto.getType()));
        return recipeIngredient;
    }

    public RecipeIngredientTemplate update(Long id, RecipeIngredientTemplate.RecipeIngredientTemplateDTO updatedRecipeIngredient) {
        RecipeIngredientTemplate recipeIngredient = getById(id);
        recipeIngredient.setAmount(updatedRecipeIngredient.getAmount());
        recipeIngredient.setType(IngredientType.valueOf(updatedRecipeIngredient.getType()));
        return recipeIngredientTemplateRepository.save(recipeIngredient);
    }

    public void delete(Long id) {
        recipeIngredientTemplateRepository.deleteById(id);
    }
}
