package com.khomchenko.proselyteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableCaching
@EnableWebFlux
@EnableScheduling
public class ProselyteapiApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProselyteapiApplication.class, args);
	}













}
