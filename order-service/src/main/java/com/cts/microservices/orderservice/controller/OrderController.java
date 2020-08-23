package com.cts.microservices.orderservice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.microservices.orderservice.model.CartResponse;
import com.cts.microservices.orderservice.model.Order;
import com.cts.microservices.orderservice.proxy.CartServiceProxy;
import com.cts.microservices.orderservice.repository.OrderRepository;
import com.cts.microservices.orderservice.service.SequenceGeneratorService;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private CartServiceProxy cartServiceProxy;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	
	@RequestMapping(value = "/{userid}", method = RequestMethod.POST)
	public Order postOrder(@PathVariable String userid, @RequestBody Order order) {
		LOG.info("Get Cart Details by User Id: {}.", userid);
		List<CartResponse> cartsresult= cartServiceProxy.retrieveCart(userid);
		order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
		final double totalAmount=cartsresult.stream().map( obj -> obj.getTotalCost()).reduce(0.0, Double::sum);
		order.setAmount(totalAmount);
		order.setUserid(userid);
		order.setOrderdate(LocalDateTime.now());
		LOG.info("Save the Cart Details: {}.", order);
		orderRepository.save(order);
		cartsresult.stream().forEach(cart -> {
			cartServiceProxy.deleteCart(cart.getCart());
		});
		return order;
	}

}
