package com.cts.microservices.cartservice.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cts.microservices.cartservice.model.Cart;

public interface CartRepository extends MongoRepository<Cart, Integer> {
	@Query("{'userid' : ?0}")
	Collection<Cart> findByUserId(String userId);
}
