package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.service.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;



    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id " + id));
    }

    public Page<Ingredient> getAll(Pageable pageable, String name) {
        if (name == null) {
            return ingredientRepository.findAll(pageable);
        } else {
            return ingredientRepository.findByNameContainingIgnoreCase(name, pageable);
        }
    }
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}
