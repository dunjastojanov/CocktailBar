package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import com.ftn.sbnz.model.event.*;
import com.ftn.sbnz.service.repository.EventRepository;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventPlanningService {


    @Autowired
    private Map<String, KieSession> kieSessions;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    public EventDisplay planEvent(Long id, EventPreferences eventPreferences) {
        Event event = calculateEventMenu(id, eventPreferences);
        return new EventDisplay(event.getMenu(), calculateIngredients(eventPreferences, event));
    }

    private Event calculateEventMenu(Long id, EventPreferences eventPreferences) {
        KieSession kieSession = kieSessions.get("event_planning");
        if (kieSession != null) {
            kieSession.insert(new EventHours(eventPreferences.getEventHours()));
            kieSession.insert(new GuestAmount(eventPreferences.getGuestAmount()));
            kieSession.insert(new MaleGuestAmount(id, eventPreferences.getMaleGuestAmount()));
            kieSession.insert(new FemaleGuestAmount(id, eventPreferences.getFemaleGuestAmount()));
            kieSession.insert(new EventIngredientList(new ArrayList<>()));
            kieSession.insert(eventService.getEvent(id));
            eventService.addGlassPreference(eventPreferences.getEventType(), kieSession);

            kieSession.fireAllRules();

            Collection<?> objects = kieSession.getObjects();

            Event event = objects.stream()
                    .filter(object -> object instanceof Event)
                    .map(object -> (Event) object)
                    .findFirst()
                    .orElse(null);

            removeObjectsFromSession(kieSession, objects);

            if (event == null) {
                throw new RuntimeException("Event menu cannot be calculated");
            }
            event.setType(eventPreferences.getEventType());
            return eventRepository.save(event);

        } else {
            throw new RuntimeException("Session is null.");
        }
    }

    private List<RecipeIngredient.RecipeIngredientDisplay> calculateIngredients(EventPreferences eventPreferences, Event event) {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kieContainer = kieServices
                .newKieContainer(kieServices.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));

        KieSession kieSession = kieContainer.newKieSession("eventIngredientsKSession");

        if (kieSession != null) {
            kieSession.insert(new EventHours(eventPreferences.getEventHours()));
            kieSession.insert(new GuestAmount(eventPreferences.getGuestAmount()));
            event.getMenu().forEach(cocktail -> {
                cocktail.getRecipeIngredients().forEach(kieSession::insert);
            });
            kieSession.fireAllRules();

            Collection<?> objects = kieSession.getObjects();
            kieSession.dispose();

            return (List<RecipeIngredient.RecipeIngredientDisplay>) objects.stream().filter(o -> o instanceof RecipeIngredient.RecipeIngredientDisplay).collect(Collectors.toList());
        } else {
            throw new RuntimeException("Session is null.");
        }
    }


    private static void removeObjectsFromSession(KieSession kieSession, Collection<?> objects) {
        for (Object object : objects) {
            if (!(object instanceof Cocktail || object instanceof Ingredient)) {
                kieSession.delete(kieSession.getFactHandle(object));
            }
        }
    }


}
