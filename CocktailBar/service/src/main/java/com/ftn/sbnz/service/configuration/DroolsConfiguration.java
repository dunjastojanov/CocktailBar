package com.ftn.sbnz.service.configuration;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.inventory.IngredientInventoryCEPEvent;
import com.ftn.sbnz.model.template.CocktailTemplate;
import com.ftn.sbnz.service.repository.CocktailRepository;
import com.ftn.sbnz.service.repository.CocktailTemplateRepository;
import com.ftn.sbnz.service.repository.IngredientInventoryRepository;
import com.ftn.sbnz.service.repository.IngredientRepository;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@Configuration
public class DroolsConfiguration {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private CocktailTemplateRepository cocktailTemplateRepository;

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
        addCocktailsFromTemplatesToSession(cocktailRecommendationKSession);
        sessions.put("cocktail_recommendation", cocktailRecommendationKSession);

        KieSession eventPlanningKSession = kieContainer.newKieSession("eventPlanningKSession");
        addCocktailsToSession(eventPlanningKSession);
        sessions.put("event_planning", eventPlanningKSession);

        KieSession inventoryTrackingSession = kieContainer.newKieSession("inventoryTrackingKSession");
        addIngredientInventoryCEP(inventoryTrackingSession);
        sessions.put("inventory_tracking", inventoryTrackingSession);

        KieSession menuUpdateKSession = kieContainer.newKieSession("menuUpdateKSession");
        addCocktailsToSession(menuUpdateKSession);
        sessions.put("menu_update", menuUpdateKSession);

        return sessions;
    }

    private void addIngredientInventoryCEP(KieSession session) {
        ingredientInventoryRepository.findAll().forEach(ingredientInventory -> {
            Date inserted = new Date();
            inserted.setTime(System.currentTimeMillis());
            inserted.setTime(inserted.getTime() - 1000 * 60 * 15);
            session.insert(new IngredientInventoryCEPEvent(inserted, ingredientInventory.getIngredient().getId(), ingredientInventory.getAmount(), ingredientInventory.getId()));
        });
    }

    private void addIngredientsToSession(KieSession session) {
        for (Ingredient ingredient : ingredientRepository.findAll()) {
            session.insert(ingredient);
        }
    }

    private void addCocktailsToSession(KieSession session) {
        List<Cocktail> cocktails = (List<Cocktail>) cocktailRepository.findAll();
        cocktails.sort(Comparator.comparing(Cocktail::getGlass).thenComparing(Cocktail::getAlcoholStrength));
        for (Cocktail cocktail : cocktails) {
            String flavors = cocktail.getIngredients().stream().map(ingredient -> ingredient.getFlavor().name()).collect(Collectors.toSet()).stream().sorted().collect(Collectors.joining(", "));
            System.out.println(cocktail.getGlass() + "-" + cocktail.getAlcoholStrength() + "-[" + flavors + "]");
            session.insert(cocktail);
        }
    }


    private void addCocktailsFromTemplatesToSession(KieSession session) {
        InputStream inputStream = DroolsConfiguration.class.getResourceAsStream("/rules/cocktail_template/cocktail_template.drt");
        List<CocktailTemplate> templates = (List<CocktailTemplate>) cocktailTemplateRepository.findAll();
        List<CocktailTemplate.CocktailTemplateTemplate> list = templates.stream().map(CocktailTemplate.CocktailTemplateTemplate::new).collect(Collectors.toList());

        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
        String drl = objectDataCompiler.compile(list, inputStream);

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        KieSession kieSession = kieHelper.build().newKieSession();

        for (CocktailTemplate cocktailTemplate : templates) {
            kieSession.insert(cocktailTemplate);
        }

        addIngredientsToSession(kieSession);

        kieSession.fireAllRules();

        List<Cocktail> cocktails = kieSession.getObjects().stream().filter(o -> o instanceof Cocktail).map(o -> (Cocktail) o).collect(Collectors.toList());

        for (Cocktail cocktail : cocktails) {
            session.insert(cocktail);
        }

    }
}

