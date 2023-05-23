package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.event.*;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventPlanningService {


    @Autowired
    private Map<String, KieSession> kieSessions;

    public EventDisplay planEvent(Long id, EventPreferences eventPreferences) {
        KieSession kieSession = kieSessions.get("event_planning");
        if (kieSession != null) {
            kieSession.insert(getEventGlassPreference());
            kieSession.insert(new EventHours(id, eventPreferences.getEventHours()));
            kieSession.insert(new GuestAmount(id, eventPreferences.getGuestAmount()));
            kieSession.insert(new MaleGuestAmount(id, eventPreferences.getMaleGuestAmount()));
            kieSession.insert(new FemaleGuestAmount(id, eventPreferences.getFemaleGuestAmount()));
            kieSession.insert(new EventIngredientList(id, new ArrayList<>()));
            kieSession.insert(getEvent(id));

            kieSession.fireAllRules();

            Collection<?> objects = kieSession.getObjects();

            Event event = objects.stream()
                    .filter(object -> object instanceof Event)
                    .map(object -> (Event) object)
                    .findFirst()
                    .orElse(null);

            EventIngredientList eventIngredientList = objects.stream()
                    .filter(object -> object instanceof EventIngredientList)
                    .map(object -> (EventIngredientList) object)
                    .findFirst()
                    .orElse(null);

            removeObjectsFromSession(kieSession, objects);

            for (Cocktail cocktail : event.getMenu()) {
                System.out.println(cocktail.getName() + " " + cocktail.getAlcoholStrength() + " " + cocktail.getGlass());
            }

            if (eventIngredientList != null) {
                return new EventDisplay(eventIngredientList.getIngredients(), event.getMenu());

            } else throw new RuntimeException("Unable to plan an event.");

        } else {
            throw new RuntimeException("Session is null.");
        }


    }

    private static Event getEvent(Long id) {
        return new Event(id, new HashSet<>(), EventType.BIRTHDAY, LocalDateTime.now());
    }

    private static EventGlassPreference getEventGlassPreference() {
        EventGlassPreference eventGlassPreference = new EventGlassPreference(
                1L,
                List.of(Glass.SHOT_GLASS, Glass.SHOT_GLASS, Glass.SHOT_GLASS, Glass.SHOT_GLASS,
                        Glass.COCKTAIL_GLASS, Glass.COCKTAIL_GLASS,
                        Glass.COLLINS_GLASS, Glass.COLLINS_GLASS, Glass.COLLINS_GLASS, Glass.COLLINS_GLASS));
        return eventGlassPreference;
    }

    private static void removeObjectsFromSession(KieSession kieSession, Collection<?> objects) {
        for (Object object : objects) {
            if (!(object instanceof Cocktail || object instanceof Ingredient)) {
                kieSession.delete(kieSession.getFactHandle(object));
            }
        }
    }


}
