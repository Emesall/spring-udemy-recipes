package com.emesall.recipes.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.emesall.recipes.model.Recipe;

@Repository
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
