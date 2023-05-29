package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.event.EventDisplay;
import com.ftn.sbnz.model.event.EventPreferences;
import com.ftn.sbnz.service.service.EventPlanningService;
import com.ftn.sbnz.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/plan")
public class EventPlanningController {


    @Autowired
    private EventPlanningService eventPlanningService;

    @Autowired
    private EventService eventService;

    @PostMapping("/{id}")
    public EventDisplay recommendCocktails(@PathVariable Long id, @RequestBody EventPreferences eventPreferences) {
        return eventPlanningService.planEvent(id, eventPreferences);
    }

    @GetMapping
    public Long createEvent() {
        return eventService.createEvent();
    }
}
