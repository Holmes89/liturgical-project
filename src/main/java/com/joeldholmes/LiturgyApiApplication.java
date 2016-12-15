package com.joeldholmes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.katharsis.resource.registry.ResourceRegistry;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories("com.joeldholmes.repository")
@EnableCircuitBreaker
@RestController
public class LiturgyApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(LiturgyApiApplication.class, args);

	}

	  @Autowired
		private ResourceRegistry resourceRegistry;
		 
			
		@RequestMapping("/resourcesInfo")
	    public Map<?, ?> getResources() {
	        Map<String, String> result = new HashMap<>();
	        // Add all resources (i.e. Project and Task)
	        for (Class<?> clazz : resourceRegistry.getResources().keySet()) {
	           result.put(resourceRegistry.getResourceType(clazz), resourceRegistry.getResourceUrl(clazz));
	        }
	        return result;
		}
}
