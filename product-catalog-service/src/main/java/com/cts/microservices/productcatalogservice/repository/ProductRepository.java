package com.cts.microservices.productcatalogservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cts.microservices.productcatalogservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, Integer> {

}
