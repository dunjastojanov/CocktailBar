package com.ftn.sbnz.service;

import com.ftn.sbnz.service.service.CocktailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.PageRequest;

@SpringBootApplication
@EntityScan("com.ftn.sbnz.model")
public class ServiceApplication implements CommandLineRunner {

    @Autowired
    private CocktailTemplateService cocktailTemplateService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        cocktailTemplateService.getAll(PageRequest.of(0, 10000));
    }
}
