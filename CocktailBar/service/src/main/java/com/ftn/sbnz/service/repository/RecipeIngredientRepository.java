package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeIngredientRepository extends PagingAndSortingRepository<RecipeIngredient, Long> {
}
