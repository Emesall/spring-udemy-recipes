package com.emesall.recipes.services;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.model.Ingredient;

import reactor.core.publisher.Mono;

public interface IngredientService {

	Mono<Ingredient> findById(String recipeId,String id);
	Mono<IngredientCommand> findCommandById(String id,String recipeId);
	Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);
	Mono<Void> deleteIngredientById(String recipeId,String id);
}
