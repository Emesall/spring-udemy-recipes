package com.emesall.recipes.services;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.model.Recipe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService
{
	Flux<Recipe> getRecipes();
	Mono<Recipe> findById(String id);
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
	Mono<RecipeCommand> findCommandById(String id);
	Mono<Void> deleteRecipeById(String id);
}
