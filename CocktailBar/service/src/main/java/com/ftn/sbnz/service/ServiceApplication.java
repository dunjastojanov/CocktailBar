package com.ftn.sbnz.service;

import com.ftn.sbnz.service.service.EventPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ftn.sbnz.model")
public class ServiceApplication {

    @Autowired
    private EventPlanningService eventPlanningService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


}
