package com.microservices.currencyconversionservice.Entity;

import java.util.List;

public interface ExchangeService {

	public List<Object> retrieveExchnageValue(String from,String to);
}
