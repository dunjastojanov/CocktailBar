package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.event.EventDisplay;
import com.ftn.sbnz.model.event.EventType;
import com.ftn.sbnz.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    private EventDisplay makeOrder(@RequestParam String cocktailName, @RequestParam(required = false) EventType eventType){
        return orderService.makeOrder(cocktailName, eventType);
    }
}
