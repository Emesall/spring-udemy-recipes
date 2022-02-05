package com.emesall.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.recipes.model.Recipe;
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
