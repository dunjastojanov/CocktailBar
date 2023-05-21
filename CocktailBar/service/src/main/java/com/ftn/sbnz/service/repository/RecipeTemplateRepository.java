package com.ftn.sbnz.service.repository;

import com.ftn.sbnz.model.template.RecipeTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeTemplateRepository extends PagingAndSortingRepository<RecipeTemplate, Long> {
}
