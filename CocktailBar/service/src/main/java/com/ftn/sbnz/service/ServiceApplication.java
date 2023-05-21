package com.ftn.sbnz.service;

import com.ftn.sbnz.model.cocktail.Flavor;
import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.model.preference.AlcoholStrength;
import com.ftn.sbnz.model.preference.TastePreference;
import com.ftn.sbnz.service.service.CocktailRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@SpringBootApplication
@EntityScan("com.ftn.sbnz.model")
public class ServiceApplication implements CommandLineRunner {

    @Autowired
    private CocktailRecommendationService cocktailRecommendationService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        TastePreference tastePreference = new TastePreference();
        tastePreference.setGlass(Glass.COCKTAIL_GLASS);
        tastePreference.setAlcoholStrength(AlcoholStrength.LIGHT);
        tastePreference.setFlavors(List.of(Flavor.SWEET, Flavor.SOUR));
        cocktailRecommendationService.recommendCocktail(1L, tastePreference);

    }
}
