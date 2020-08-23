package com.cts.microservices.orderservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.microservices.orderservice.model.Cart;
import com.cts.microservices.orderservice.model.CartResponse;

@FeignClient(name="cart-service",url="http://localhost:8083")
public interface CartServiceProxy {

	@GetMapping("/api/cart/{userid}")
	public List<CartResponse> retrieveCart(@PathVariable("userid") String userId);
	
	@DeleteMapping("/api/cart")
	public List<CartResponse> deleteCart(@RequestBody Cart cart);
	
}