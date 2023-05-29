package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.cocktail.RecipeIngredient;
import com.ftn.sbnz.model.event.*;
import com.ftn.sbnz.model.preference.GlassPreference;
import com.ftn.sbnz.service.configuration.DroolsConfiguration;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventPlanningService {


    @Autowired
    private Map<String, KieSession> kieSessions;

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
            kieSession.insert(getEvent(id));
            addGlassPreference(eventPreferences.getEventType(), kieSession);

            kieSession.fireAllRules();

            Collection<?> objects = kieSession.getObjects();

            Event event = objects.stream()
                    .filter(object -> object instanceof Event)
                    .map(object -> (Event) object)
                    .findFirst()
                    .orElse(null);

            removeObjectsFromSession(kieSession, objects);

            return event;

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

    private static Event getEvent(Long id) {
        return new Event(id, new HashSet<>(), EventType.BIRTHDAY, LocalDateTime.now());
    }

    public void addGlassPreference(EventType eventType, KieSession session) {
        InputStream inputStream = DroolsConfiguration.class.getResourceAsStream("/rules/event_template/event_template.drt");
        List<Object> list = getGlassPreferenceCounts(eventType);

        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
        String drl = objectDataCompiler.compile(list, inputStream);

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        KieSession kieSession = kieHelper.build().newKieSession();
        kieSession.fireAllRules();
        kieSession.getObjects().stream()
                .filter(o -> o instanceof GlassPreference)
                .forEach(session::insert);
    }

    private static List<Object> getGlassPreferenceCounts(EventType eventType) {
        List<Object> list = new ArrayList<>();

        if (eventType == EventType.BIRTHDAY) {
            list.add(new GlassPreferenceCount(eventType, Glass.SHOT_GLASS, 4));
            list.add(new GlassPreferenceCount(eventType, Glass.COLLINS_GLASS, 4));
            list.add(new GlassPreferenceCount(eventType, Glass.MARTINI_GLASS, 2));
        } else if (eventType == EventType.WEDDING) {
            list.add(new GlassPreferenceCount(eventType, Glass.SHOT_GLASS, 2));
            list.add(new GlassPreferenceCount(eventType, Glass.COLLINS_GLASS, 3));
            list.add(new GlassPreferenceCount(eventType, Glass.MARTINI_GLASS, 3));
            list.add(new GlassPreferenceCount(eventType, Glass.WHISKEY_GLASS, 2));
        } else if (eventType == EventType.BUSINESS) {
            list.add(new GlassPreferenceCount(eventType, Glass.WHISKEY_GLASS, 4));
            list.add(new GlassPreferenceCount(eventType, Glass.COLLINS_GLASS, 4));
            list.add(new GlassPreferenceCount(eventType, Glass.MARTINI_GLASS, 2));
        } else if (eventType == EventType.BACHELOR_PARTY || eventType == EventType.BACHELORETTE_PARTY) {
            list.add(new GlassPreferenceCount(eventType, Glass.SHOT_GLASS, 6));
            list.add(new GlassPreferenceCount(eventType, Glass.COLLINS_GLASS, 2));
            list.add(new GlassPreferenceCount(eventType, Glass.MARTINI_GLASS, 2));
        }
        return list;
    }

    private static void removeObjectsFromSession(KieSession kieSession, Collection<?> objects) {
        for (Object object : objects) {
            if (!(object instanceof Cocktail || object instanceof Ingredient)) {
                kieSession.delete(kieSession.getFactHandle(object));
            }
        }
    }


}
