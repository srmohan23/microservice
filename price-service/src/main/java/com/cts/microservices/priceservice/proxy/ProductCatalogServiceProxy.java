package com.cts.microservices.priceservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cts.microservices.priceservice.model.Product;

@FeignClient(name="product-catalog-service",url="http://localhost:8081")
public interface ProductCatalogServiceProxy {
	
	@GetMapping("/api/products/{id}")
	public Product retrievePrice(@PathVariable("id") int id);

}