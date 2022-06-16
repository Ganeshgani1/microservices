package com.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.currencyexchangeservice.Entity.CurrencyExchange;
import com.microservices.currencyexchangeservice.Repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	@Value("${server.port}")
	private String environment;
	
	@Autowired
	private CurrencyExchangeRepository repo;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {
//		CurrencyExchange currencyExchange= new CurrencyExchange(1000L, from, to, 
//				BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = repo.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to find data for" + from + "to" + to);
		}
		System.out.println(currencyExchange);
		String port=environment;
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}
