package com.company.powerful_application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.company.powerful_application.domain.product.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
