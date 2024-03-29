package com.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private Logger logger=LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name = "default", fallbackMethod = "HardcodedResponse")
	@CircuitBreaker(name = "default", fallbackMethod = "HardcodedResponse")
	public String sampleApi() {
		logger.info("Sample API call recieved");
	ResponseEntity<String> responseEntity=	new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return responseEntity.getBody();
	}
	
	public String HardcodedResponse(Exception ex) {
		return "fallback-response";
	}

}
