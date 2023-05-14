package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.template.CocktailTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CocktailTemplateRepository extends PagingAndSortingRepository<CocktailTemplate, Long> {
}
