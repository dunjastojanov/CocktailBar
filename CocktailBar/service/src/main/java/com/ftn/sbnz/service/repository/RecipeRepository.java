package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.cocktail.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
}
