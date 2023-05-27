package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.cocktail.Cocktail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends PagingAndSortingRepository<Cocktail, Long> {
    Page<Cocktail> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
