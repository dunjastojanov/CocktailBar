package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.cocktail.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {
    Page<Ingredient> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
