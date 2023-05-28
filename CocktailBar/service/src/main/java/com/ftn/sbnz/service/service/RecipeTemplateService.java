package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.template.RecipeTemplate;
import com.ftn.sbnz.service.repository.RecipeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class RecipeTemplateService {

    @Autowired
    private RecipeTemplateRepository recipeTemplateRepository;

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @Autowired
    private RecipeIngredientTemplateService recipeIngredientTemplateService;


    public RecipeTemplate getById(Long id) {
        return recipeTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe template not found with id " + id));
    }

    public Page<RecipeTemplate> getAll(Pageable pageable) {
        return recipeTemplateRepository.findAll(pageable);
    }

    public RecipeTemplate create(RecipeTemplate.RecipeTemplateDTO recipe) {
        return recipeTemplateRepository.save(getRecipe(recipe));
    }

    private RecipeTemplate getRecipe(RecipeTemplate.RecipeTemplateDTO dto) {
        RecipeTemplate recipe = new RecipeTemplate();
        recipe.setRecipeIngredients(dto.getRecipeIngredients().stream().map(recipeIngredientId -> recipeIngredientService.getById(recipeIngredientId)).collect(Collectors.toSet()));
        recipe.setRecipeIngredientTemplate(dto.getRecipeIngredientTemplate());
        recipe.setInstructions(dto.getInstructions());
        return recipe;
    }

    public RecipeTemplate update(Long id, RecipeTemplate.RecipeTemplateDTO updatedRecipe) {
        RecipeTemplate recipe = getById(id);
        recipe.setRecipeIngredients(updatedRecipe.getRecipeIngredients().stream().map(recipeIngredientId -> recipeIngredientService.getById(recipeIngredientId)).collect(Collectors.toSet()));
        recipe.setRecipeIngredientTemplate(updatedRecipe.getRecipeIngredientTemplate());
        recipe.setInstructions(updatedRecipe.getInstructions());
        return recipeTemplateRepository.save(recipe);
    }

    public void delete(Long id) {
        recipeTemplateRepository.deleteById(id);
    }
}
