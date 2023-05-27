package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.inventory.IngredientInventoryCEPEvent;
import com.ftn.sbnz.model.inventory.LowIngredientAlarm;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private Map<String, KieSession> kieSessions;

    @Autowired
    private IngredientInventoryService ingredientInventoryService;

    public void makeOrder(String cocktailName) {
        Cocktail cocktail = cocktailService.getCocktailByName(cocktailName);
        KieSession inventoryKieSession = kieSessions.get("inventory_tracking");
        KieSession eventKieSession = kieSessions.get("event_planning");
        if (inventoryKieSession != null) {
            cocktail.getRecipe().getRecipeIngredients().forEach(inventoryKieSession::insert);
            inventoryKieSession.fireAllRules();
            Collection<?> objects = inventoryKieSession.getObjects();
            ArrayList<LowIngredientAlarm> lowIngredientAlarms = new ArrayList<>();
            for (Object object : objects) {
                if (object instanceof IngredientInventoryCEPEvent) {
                    ingredientInventoryService.updateIngredientInventory((IngredientInventoryCEPEvent) object);
                } else if (object instanceof LowIngredientAlarm) {
                    lowIngredientAlarms.add((LowIngredientAlarm) object);
                }
            }
        } else {
            throw new RuntimeException("KieSession is null");
        }
        if (eventKieSession != null) {
            eventKieSession.insert(cocktail);
            eventKieSession.fireAllRules();

        } else {
            throw new RuntimeException("KieSession is null");
        }
    }

}
