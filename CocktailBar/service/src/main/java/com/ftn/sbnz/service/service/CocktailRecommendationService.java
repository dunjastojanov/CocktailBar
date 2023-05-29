package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Cocktail;
import com.ftn.sbnz.model.cocktail.Flavor;
import com.ftn.sbnz.model.cocktail.Ingredient;
import com.ftn.sbnz.model.preference.*;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CocktailRecommendationService {

    @Autowired
    private Map<String, KieSession> kieSessions;

    @Autowired
    private UserService userService;

    public List<Cocktail.CocktailDisplayDTO> recommendCocktail(Long userId, TastePreference tastePreference) {

        KieSession kieSession = kieSessions.get("cocktail_recommendation");
        if (kieSession != null) {
            kieSession.insert(new GlassPreference(userId, tastePreference.getGlass()));
            kieSession.insert(new AlcoholAmountPreference(userId, tastePreference.getAlcoholStrength().name()));
            kieSession.insert(userService.getUser(userId));

            for (Flavor flavor : tastePreference.getFlavors()) {
                kieSession.insert(new FlavorPreference(userId, flavor));
            }

            kieSession.fireAllRules();
            Collection<?> objects = kieSession.getObjects();

            PreferableCocktailList preferableCocktailList = objects.stream()
                    .filter(object -> object instanceof PreferableCocktailList)
                    .map(object -> (PreferableCocktailList) object)
                    .findFirst()
                    .orElse(null);

            removeObjectsFromSession(kieSession, objects);
            if (preferableCocktailList == null)
                throw new EntityNotFoundException("There are no cocktails with given parameters.");

            List<Cocktail.CocktailDisplayDTO> cocktails = preferableCocktailList.getCocktails().stream().map(Cocktail.CocktailDisplayDTO::new).collect(Collectors.toList());

            if (cocktails.size() == 0)
                throw new EntityNotFoundException("There are no cocktails with given parameters.");

            return cocktails;
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
