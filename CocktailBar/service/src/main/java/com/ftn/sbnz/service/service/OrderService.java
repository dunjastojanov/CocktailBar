package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.OrderEvent;
import com.ftn.sbnz.model.inventory.IngredientInventoryCEPEvent;
import com.ftn.sbnz.model.inventory.LowIngredientAlarm;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        updateMenu(cocktail);
        updateInventory(cocktail);
    }

    public void updateInventory(Cocktail cocktail) {
        KieSession inventoryKieSession = kieSessions.get("inventory_tracking");
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
    }

    public void updateMenu(Cocktail cocktail) {
        KieSession menuUpdateKieSession = kieSessions.get("menu_update");
        if (menuUpdateKieSession != null) {
            Date executionTime = new Date();
            executionTime.setTime(System.currentTimeMillis());
            OrderEvent orderEvent = new OrderEvent(executionTime, cocktail);
            menuUpdateKieSession.insert(orderEvent);
            menuUpdateKieSession.fireAllRules();
            Collection<?> objects = menuUpdateKieSession.getObjects();
        } else {
            throw new RuntimeException("KieSession is null");
        }
    }

}
