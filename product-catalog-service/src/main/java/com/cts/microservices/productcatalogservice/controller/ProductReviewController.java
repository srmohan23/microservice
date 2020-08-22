package com.cts.microservices.productcatalogservice.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.microservices.productcatalogservice.model.Product;
import com.cts.microservices.productcatalogservice.model.Review;
import com.cts.microservices.productcatalogservice.repository.ProductRepository;
import com.cts.microservices.productcatalogservice.repository.ReviewRepository;
import com.cts.microservices.productcatalogservice.service.SequenceGeneratorService;

@RestController
@RequestMapping(value = "/api/products")
public class ProductReviewController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	private final ProductRepository productRepository;
	private final ReviewRepository reviewRepository;

	public ProductReviewController(ProductRepository productRepository, ReviewRepository reviewRepository) {
		this.productRepository = productRepository;
		this.reviewRepository=reviewRepository;
	}
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		LOG.info("Getting all Products.");
		return productRepository.findAll();
	}
	

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable int productId) {
		LOG.info("Getting product by Product Id: {}.", productId);
		return productRepository.findById(productId).get();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Product postProduct(@RequestBody Product product) {
		LOG.info("Save product : {}.", product);
		product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
		productRepository.save(product);
		
		return product;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public List<Product> postProduct(@RequestBody List<Product> products) {
		LOG.info("Save all product : {}.", products);
		products.stream().forEach(product -> {
			product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
			productRepository.save(product);
		});
		
		return products;
	}
	
	@RequestMapping(value = "/{productId}/reviews", method = RequestMethod.GET)
	public Collection<Review> getReview(@PathVariable int productId) {
		LOG.info("Getting product by Product Id: {}.", productId);
		return reviewRepository.findByProjectId(productId);
	}
	
	@RequestMapping(value = "/{productId}/reviews", method = RequestMethod.POST)
	public Review postReview(@PathVariable int productId,@RequestBody Review review) {
		LOG.info("Save Review : {}.", review);
		Product product= productRepository.findById(productId).get();
		review.setProduct(product);
		review.setReviewid(sequenceGeneratorService.generateSequence(Review.SEQUENCE_NAME));
		reviewRepository.save(review);
		
		return review;
	}


}