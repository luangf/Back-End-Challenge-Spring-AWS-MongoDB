package com.company.powerful_application.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.company.powerful_application.domain.category.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

}
