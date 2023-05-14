package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.model.template.CocktailTemplate;
import com.ftn.sbnz.service.repository.CocktailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CocktailTemplateService {

    @Autowired
    private CocktailTemplateRepository cocktailTemplateRepository;

    public CocktailTemplate getById(Long id) {
        return cocktailTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cocktail template not found with id " + id));
    }

    public Page<CocktailTemplate> getAll(Pageable pageable) {
        return cocktailTemplateRepository.findAll(pageable);
    }

    public CocktailTemplate create(CocktailTemplate.CocktailTemplateDTO cocktail) {
        return cocktailTemplateRepository.save(getCocktail(cocktail));
    }

    private CocktailTemplate getCocktail(CocktailTemplate.CocktailTemplateDTO dto) {
        CocktailTemplate cocktail = new CocktailTemplate();
        cocktail.setRecipe(null);
        cocktail.setGlass(Glass.valueOf(dto.getGlass()));
        return cocktail;
    }

    public CocktailTemplate update(Long id, CocktailTemplate.CocktailTemplateDTO updatedCocktail) {
        CocktailTemplate cocktail = getById(id);
        cocktail.setRecipe(null);
        cocktail.setGlass(Glass.valueOf(updatedCocktail.getGlass()));
        return cocktailTemplateRepository.save(cocktail);
    }

    public void delete(Long id) {
        cocktailTemplateRepository.deleteById(id);
    }
}
