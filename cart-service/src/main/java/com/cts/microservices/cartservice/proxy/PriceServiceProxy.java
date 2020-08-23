package com.cts.microservices.cartservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="price-service",url="http://localhost:8082")
public interface PriceServiceProxy {

	@GetMapping("/api/price/{id}")
	public double retrievePrice(@PathVariable("id") int id);
}