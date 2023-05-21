package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.preference.TastePreference;
import com.ftn.sbnz.service.service.CocktailRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocktail/recommendation")
public class CocktailRecommendationController {

    @Autowired
    private CocktailRecommendationService cocktailRecommendationService;

    @PostMapping("/{id}")
    public List<Cocktail> getById(@PathVariable Long id, @RequestBody TastePreference tastePreference) {
        return cocktailRecommendationService.recommendCocktail(id, tastePreference);
    }
}
