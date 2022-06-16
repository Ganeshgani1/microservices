package com.microservices.currencyconversionservice.Entity;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange-service", url="localhost:8000",decode404 = true)
@FeignClient(name="currency-exchange-service")
public interface CurrencyExchangeProxy{
	
	public static final String exchangeUrl = "localhost:8000//currency-exchange/from/{from}/to/{to}";

	@GetMapping(exchangeUrl)
	public List<Object> retrieveExchnageValue(
			@PathVariable(value="from") String from,
			@PathVariable(value = "to") String to);
}
