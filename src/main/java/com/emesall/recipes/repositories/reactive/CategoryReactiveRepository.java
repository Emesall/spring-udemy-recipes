package com.emesall.recipes.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.emesall.recipes.model.Category;

@Repository
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

}
