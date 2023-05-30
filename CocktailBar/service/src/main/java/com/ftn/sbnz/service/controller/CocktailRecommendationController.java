package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.preference.TastePreference;
import com.ftn.sbnz.service.service.CocktailRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cocktail/recommendation")
public class CocktailRecommendationController {

    @Autowired
    private CocktailRecommendationService cocktailRecommendationService;

    @PostMapping
    public List<Cocktail.CocktailDisplayDTO> recommendCocktails( @RequestBody TastePreference tastePreference) {
        return cocktailRecommendationService.recommendCocktail(tastePreference);
    }
}
