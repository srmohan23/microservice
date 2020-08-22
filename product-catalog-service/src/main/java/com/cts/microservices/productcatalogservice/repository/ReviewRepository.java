package com.cts.microservices.productcatalogservice.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cts.microservices.productcatalogservice.model.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {

	@Query("{'product.$id' : ?0}")
	Collection<Review> findByProjectId(int productId);
}
