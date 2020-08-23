package com.cts.microservices.orderservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cts.microservices.orderservice.model.Order;

public interface OrderRepository extends MongoRepository<Order, Integer> {

}
