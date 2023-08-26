package com.khomchenko.proselyteapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/health")
public class HealthRestControllerV1 {

    @GetMapping
    public Mono<String> testApp(){
        return Mono.just("OK");
    }
}
