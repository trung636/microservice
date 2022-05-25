package com.microservice.demo.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private CurrencyExChangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to) {
		CurrencyExchange result = repository.findByFromAndTo(from, to);
		if(result == null) {
			throw new RuntimeException("Unable to find data for " + from +" to "+ to);
		}
		
		String port = environment.getProperty("local.server.port");
		result.setEnvironment(port);
		return result;
	}

}
