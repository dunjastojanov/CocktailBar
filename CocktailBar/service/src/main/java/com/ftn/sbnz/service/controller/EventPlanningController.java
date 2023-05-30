package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.event.EventDisplay;
import com.ftn.sbnz.model.event.EventPreferences;
import com.ftn.sbnz.service.service.EventPlanningService;
import com.ftn.sbnz.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event/plan")
public class EventPlanningController {


    @Autowired
    private EventPlanningService eventPlanningService;

    @Autowired
    private EventService eventService;

    @PostMapping
    public EventDisplay recommendCocktails(@RequestBody EventPreferences eventPreferences) {
        return eventPlanningService.planEvent(eventPreferences);
    }

}
