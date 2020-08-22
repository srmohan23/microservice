package com.cts.microservices.productcatalogservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.cts.microservices.productcatalogservice.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
