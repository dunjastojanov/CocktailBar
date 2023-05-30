package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.OrderEvent;
import com.ftn.sbnz.model.event.Event;
import com.ftn.sbnz.model.event.EventDisplay;
import com.ftn.sbnz.model.event.EventType;
import com.ftn.sbnz.model.inventory.IngredientInventoryCEPEvent;
import com.ftn.sbnz.model.inventory.LowIngredientAlarm;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private EventService eventService;

    @Autowired
    private Map<String, KieSession> kieSessions;

    @Autowired
    private IngredientInventoryService ingredientInventoryService;


    public EventDisplay makeOrder(String cocktailName, EventType eventType) {
        Cocktail cocktail = cocktailService.getCocktailByName(cocktailName);
        EventDisplay eventDisplay = new EventDisplay();
        List<LowIngredientAlarm> alarms = updateInventory(cocktail);
        eventDisplay.setAlarms(alarms);
        if (eventType != null) {
            Event event = updateMenu(cocktail, eventType);
            eventDisplay.setCocktails(event.getMenu().stream().map(Cocktail.CocktailDisplayDTO::new).collect(Collectors.toList()));
        }
        eventDisplay.setEventType(eventType);
        return eventDisplay;
    }

    public List<LowIngredientAlarm> updateInventory(Cocktail cocktail) {
        KieSession inventoryKieSession = kieSessions.get("inventory_tracking");
        if (inventoryKieSession != null) {
            cocktail.getRecipe().getRecipeIngredients().forEach(inventoryKieSession::insert);
            inventoryKieSession.fireAllRules();
            Collection<?> objects = inventoryKieSession.getObjects();
            ArrayList<LowIngredientAlarm> lowIngredientAlarms = new ArrayList<>();
            for (Object object : objects) {
                if (object instanceof IngredientInventoryCEPEvent) {
                    ingredientInventoryService.updateIngredientInventory((IngredientInventoryCEPEvent) object);
                    inventoryKieSession.delete(inventoryKieSession.getFactHandle(object));
                } else if (object instanceof LowIngredientAlarm) {
                    lowIngredientAlarms.add((LowIngredientAlarm) object);
                    inventoryKieSession.delete(inventoryKieSession.getFactHandle(object));
                }
            }

            return lowIngredientAlarms;
        } else {
            throw new RuntimeException("KieSession is null");
        }
    }

    public Event updateMenu(Cocktail cocktail, EventType eventType) {

        Event event = new Event();
        event.setType(eventType);
        event.setMenu(new HashSet<>());
        KieSession menuUpdateKieSession = kieSessions.get("menu_update");

        if (menuUpdateKieSession != null) {
            Date executionTime = new Date();
            executionTime.setTime(System.currentTimeMillis());
            OrderEvent orderEvent = new OrderEvent(executionTime, cocktail);
            menuUpdateKieSession.insert(orderEvent);
            menuUpdateKieSession.insert(event);
            eventService.addGlassPreference(event.getType(), menuUpdateKieSession);

            menuUpdateKieSession.fireAllRules();
            Collection<?> objects = menuUpdateKieSession.getObjects();

            Optional<Event> optional = (Optional<Event>) objects.stream().filter(o -> o instanceof Event).findFirst();

            if (optional.isEmpty()) {
                throw new RuntimeException("Cannot update menu.");
            }

            return optional.get();
        } else {
            throw new RuntimeException("KieSession is null");
        }
    }

}
