package com.emesall.recipes.repositories;

import org.springframework.data.repository.CrudRepository;

import com.emesall.recipes.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
