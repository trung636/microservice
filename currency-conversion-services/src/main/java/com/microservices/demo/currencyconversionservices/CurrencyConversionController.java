package com.microservices.demo.currencyconversionservices;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> response = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/USD/to/INR", CurrencyConversion.class, uriVariables);
		CurrencyConversion result = response.getBody();
		return new CurrencyConversion(
					1000L, 
					from, 
					to, 
					quantity, 
					result.getConversionMultiple(), 
					quantity.multiply(quantity), 
					result.getEnvironment());
	}
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		CurrencyConversion result = proxy.retrieveExchangeValue(from, to);
		return new CurrencyConversion(
					1000L, 
					from, 
					to, 
					quantity, 
					result.getConversionMultiple(), 
					quantity.multiply(quantity), 
					result.getEnvironment());
	}
}
