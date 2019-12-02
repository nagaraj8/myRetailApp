package com.myretail.api.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * Target Case Study
 * @author Nagaraj Hegde
 *
 */
@SpringBootApplication
@EnableMongoRepositories("com.myretail.api.repository")
@ComponentScan("com.myretail.api.exception")
@ComponentScan("com.myretail.api.controller")
@ComponentScan("com.myretail.api.service")
@ComponentScan("com.myretail.api.vo")
@ComponentScan("com.myretail.api.configuration")
public class MyRetailLoader {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailLoader.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
}
