package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.service.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CocktailService {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private RecipeService recipeService;


    public Cocktail getById(Long id) {
        return cocktailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cocktail not found with id " + id));
    }

    public Page<Cocktail> getAll(Pageable pageable, String name) {
        if (name == null) {
            return cocktailRepository.findAll(pageable);
        } else {
            return cocktailRepository.findByNameContainingIgnoreCase(name, pageable);
        }
    }

    public Cocktail create(Cocktail.CocktailDTO cocktail) {
        return cocktailRepository.save(getCocktail(cocktail));
    }

    private Cocktail getCocktail(Cocktail.CocktailDTO dto) {
        Cocktail cocktail = new Cocktail();
        cocktail.setImage(dto.getImage());
        cocktail.setRecipe(recipeService.getById(dto.getRecipeId()));
        cocktail.setGlass(Glass.valueOf(dto.getGlass()));
        cocktail.setName(dto.getName());
        return cocktail;
    }

    public Cocktail update(Long id, Cocktail.CocktailDTO updatedCocktail) {
        Cocktail cocktail = getById(id);
        cocktail.setImage(updatedCocktail.getImage());
        cocktail.setRecipe(recipeService.getById(updatedCocktail.getRecipeId()));
        cocktail.setGlass(Glass.valueOf(updatedCocktail.getGlass()));
        cocktail.setName(updatedCocktail.getName());
        return cocktailRepository.save(cocktail);
    }

    public void delete(Long id) {
        cocktailRepository.deleteById(id);
    }
}
