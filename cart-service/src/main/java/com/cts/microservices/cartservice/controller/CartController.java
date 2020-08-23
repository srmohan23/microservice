package com.cts.microservices.cartservice.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.microservices.cartservice.model.Cart;
import com.cts.microservices.cartservice.model.CartResponse;
import com.cts.microservices.cartservice.proxy.PriceServiceProxy;
import com.cts.microservices.cartservice.repository.CartRepository;
import com.cts.microservices.cartservice.service.SequenceGeneratorService;

@RestController
@RequestMapping(value = "/api/cart")
public class CartController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private PriceServiceProxy priceServiceProxy;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@RequestMapping(value = "/{userid}", method = RequestMethod.GET)
	public List<CartResponse> postCart(@PathVariable String userid) {
		LOG.info("Get the Cart Details by User Id: {}.", userid);
		Collection<Cart> carts= cartRepository.findByUserId(userid);
		List<CartResponse> cartsRes=new ArrayList<>();
		carts.forEach(obj -> {
			CartResponse cartRes =new CartResponse();
			double totalCost=obj.getPrice()*obj.getQty();
			cartRes.setCart(obj);
			cartRes.setTotalCost(totalCost);
			cartsRes.add(cartRes);
		});
		
		return cartsRes;
	}

	@RequestMapping(value = "/{productId}/{userid}", method = RequestMethod.POST)
	public CartResponse postCart(@PathVariable int productId,@PathVariable String userid, @RequestBody Cart cart) {
		LOG.info("Get the Price by Product Id: {}.", productId);
		double price= priceServiceProxy.retrievePrice(productId);
		cart.setId(sequenceGeneratorService.generateSequence(Cart.SEQUENCE_NAME));
		cart.setPrice(price);
		cart.setUserId(userid);
		LOG.info("Save the Cart Details: {}.", cart);
		cartRepository.save(cart);
		CartResponse response=new CartResponse();
		response.setCart(cart);
		response.setTotalCost(cart.getPrice()*cart.getQty());
		return response;
	}
	
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public  ResponseEntity<Object> deleteCart(@RequestBody Cart cart) {
		LOG.info("Delete the Cart : {}.", cart);
		cartRepository.delete(cart);		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.DELETE)
	public  ResponseEntity<Object> deleteAllCart(@RequestBody List<Cart> carts) {
		LOG.info("Delete the Cart : {}.", carts);
		carts.stream().forEach( obj -> cartRepository.delete(obj));
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
