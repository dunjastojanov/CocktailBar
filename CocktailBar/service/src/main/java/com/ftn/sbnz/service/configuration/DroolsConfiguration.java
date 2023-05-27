package com.ftn.sbnz.service.configuration;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.service.repository.CocktailRepository;
import com.ftn.sbnz.service.repository.IngredientRepository;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DroolsConfiguration {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientInventoryRepository ingredientInventoryRepository;

    @Bean
    public Map<String, KieSession> kieSessions() {
        Map<String, KieSession> sessions = new HashMap<>();

        KieServices kieServices = KieServices.Factory.get();

        KieContainer kieContainer = kieServices
                .newKieContainer(kieServices.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = kieServices.newKieScanner(kieContainer);
        kScanner.start(1000);

        KieSession cocktailRecommendationKSession = kieContainer.newKieSession("cocktailRecommendationKSession");
        addCocktailsToSession(cocktailRecommendationKSession);
        addIngredientsToSession(cocktailRecommendationKSession);
        sessions.put("cocktail_recommendation", cocktailRecommendationKSession);

        KieSession eventPlanningKSession = kieContainer.newKieSession("eventPlanningKSession");
        addCocktailsToSession(eventPlanningKSession);
        sessions.put("event_planning", eventPlanningKSession);

        KieSession inventoryTrackingSession = kieContainer.newKieSession("inventoryTrackingKSession");

        ingredientInventoryRepository.findAll().forEach(ingredientInventory -> {
            Date inserted=new Date();
            inserted.setTime(System.currentTimeMillis());
            inserted.setTime(inserted.getTime()-1000*60*15);
            inventoryTrackingSession.insert(new IngredientInventoryCEPEvent(inserted, ingredientInventory.getIngredient().getId(), ingredientInventory.getAmount(), ingredientInventory.getId()));
        });
        sessions.put("inventory_tracking", inventoryTrackingSession);
        sessions.put("menu_update", kieContainer.newKieSession("menuUpdateKSession"));

        return sessions;
    }

    private void addIngredientsToSession(KieSession cocktailRecommendationKSession) {
        for (Ingredient ingredient: ingredientRepository.findAll()) {
            cocktailRecommendationKSession.insert(ingredient);
        }
    }

    private void addCocktailsToSession(KieSession cocktailRecommendationKSession) {
        for (Cocktail cocktail: cocktailRepository.findAll()){
            cocktailRecommendationKSession.insert(cocktail);
        }
    }
}

