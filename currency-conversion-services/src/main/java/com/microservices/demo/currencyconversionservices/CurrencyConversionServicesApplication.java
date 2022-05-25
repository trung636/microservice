package com.microservices.demo.currencyconversionservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyConversionServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServicesApplication.class, args);
	}

}
