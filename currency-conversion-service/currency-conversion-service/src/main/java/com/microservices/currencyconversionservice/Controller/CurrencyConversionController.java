package com.microservices.currencyconversionservice.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.currencyconversionservice.Entity.CurrencyConversion;
import com.microservices.currencyconversionservice.Entity.CurrencyExchangeProxy;

@RestController
@EnableFeignClients(basePackageClasses = CurrencyExchangeProxy.class)
public class CurrencyConversionController {
	
	@Autowired
	//@org.springframework.beans.factory.annotation.Autowired(required=true)
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/Currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion clculateCurrencyConversion(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to,
			@PathVariable BigDecimal quantity) {
		try {
			RestTemplate restTemplate= new RestTemplate();
			String resourceUrl="http://localhost:8000/currency-exchange/from/"+from+"/to/"+to;
//		HashMap<String, String> uriVariables = new HashMap<String, String>();
//		uriVariables.put("from", from);
//		uriVariables.put("to", to);
//		HttpHeaders headers = new HttpHeaders();
//	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	      HttpEntity<CurrencyConversion> entity = new HttpEntity<CurrencyConversion>(headers);
//		CurrencyConversion currencyConversion = new RestTemplate().getForObject(
//				"http://localhost:8000/currencey-exchange/from/USD/to/INR", CurrencyConversion.class);

		ResponseEntity<CurrencyConversion> response =restTemplate.getForEntity(resourceUrl, CurrencyConversion.class);
//	      ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().exchange(
// 				"http://localhost:8000/currencey-exchange/from/"+from+"/to/"+to, HttpMethod.GET, entity,CurrencyConversion.class);

		ObjectMapper mapper = new ObjectMapper();
		CurrencyConversion currencyConversion = response.getBody();
		return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());

	}catch (Exception e) {
		System.out.println("");
	}
		return null;
	} 

	@GetMapping("/Currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion clculateCurrencyConversionWithFeign(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to,
			@PathVariable BigDecimal quantity) {
				List<Object> obj=proxy.retrieveExchnageValue(from, to);
				CurrencyConversion CurrencyConversion;
//				return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
//						currencyConversion.getConversionMultiple(),
//						quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
				return null;
	}
}
