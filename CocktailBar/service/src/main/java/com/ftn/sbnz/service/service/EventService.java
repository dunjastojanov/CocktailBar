package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cocktail.Glass;
import com.ftn.sbnz.model.event.Event;
import com.ftn.sbnz.model.event.EventType;
import com.ftn.sbnz.model.event.GlassPreferenceCount;
import com.ftn.sbnz.model.preference.GlassPreference;
import com.ftn.sbnz.service.configuration.DroolsConfiguration;
import com.ftn.sbnz.service.repository.EventRepository;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public Event getEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new EntityNotFoundException("There is no event with given id.");
        }
        return event.get();
    }

    public Long createEvent() {
        return eventRepository.save(new Event()).getId();
    }

    public List<Object> getGlassPreferenceCounts(EventType eventType) {
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

    public void addGlassPreference(EventType eventType, KieSession session) {
        InputStream inputStream = DroolsConfiguration.class.getResourceAsStream("/rules/event_template/event_template.drt");
        List<Object> list = getGlassPreferenceCounts(eventType);

        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
        String drl = objectDataCompiler.compile(list, inputStream);

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        KieSession kieSession = kieHelper.build().newKieSession();
        kieSession.fireAllRules();
        kieSession.getObjects().stream().filter(o -> o instanceof GlassPreference).forEach(session::insert);
    }
}
