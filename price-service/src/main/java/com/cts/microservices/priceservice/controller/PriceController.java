package com.cts.microservices.priceservice.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cts.microservices.priceservice.proxy.ProductCatalogServiceProxy;

@RestController
@RequestMapping(value = "/api/price")
public class PriceController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductCatalogServiceProxy productCatalogServiceProxy;
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public Double getPrice(@PathVariable int productId) {
		LOG.info("Getting price by Product Id: {}.", productId);
		return productCatalogServiceProxy.retrievePrice(productId).getPrice();
	}

}
