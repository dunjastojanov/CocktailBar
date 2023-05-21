package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import com.ftn.sbnz.service.repository.RecipeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RecipeIngredientService {

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private IngredientService ingredientService;
    

    public RecipeIngredient getById(Long id) {
        return recipeIngredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe ingredient not found with id " + id));
    }

    public Page<RecipeIngredient> getAll(Pageable pageable) {
        return recipeIngredientRepository.findAll(pageable);
    }

    public RecipeIngredient create(RecipeIngredient.RecipeIngredientDTO recipeIngredient) {
        return recipeIngredientRepository.save(getRecipeIngredient(recipeIngredient));
    }

    private RecipeIngredient getRecipeIngredient(RecipeIngredient.RecipeIngredientDTO dto) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setAmount(dto.getAmount());
        recipeIngredient.setIngredient(ingredientService.getById(dto.getIngredientId()));
        return recipeIngredient;
    }

    public RecipeIngredient update(Long id, RecipeIngredient.RecipeIngredientDTO updatedRecipeIngredient) {
        RecipeIngredient recipeIngredient = getById(id);
        recipeIngredient.setAmount(updatedRecipeIngredient.getAmount());
        recipeIngredient.setIngredient(ingredientService.getById(updatedRecipeIngredient.getIngredientId()));
        return recipeIngredientRepository.save(recipeIngredient);
    }

    public void delete(Long id) {
        recipeIngredientRepository.deleteById(id);
    }
}
