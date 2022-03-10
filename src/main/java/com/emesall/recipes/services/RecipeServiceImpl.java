package com.emesall.recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.converters.RecipeCommandToRecipe;
import com.emesall.recipes.converters.RecipeToRecipeCommand;
import com.emesall.recipes.exceptions.NotFoundException;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	@Autowired
	public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes() {
		return recipeRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeRepository.findById(id)
				.switchIfEmpty(Mono.error(new NotFoundException("No recipe found")));
	}

	@Override
	@Transactional
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {

		return recipeRepository.save(recipeCommandToRecipe.convert(command))
			.map(recipe -> {
				
			RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
			recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));
			
			return recipeCommand;
		});
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {

		return findById(id).map(recipe -> {
			RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

			recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));

			return recipeCommand;
		});

	}

	@Override
	public Mono<Void> deleteRecipeById(String id) {
		
		return recipeRepository.deleteById(id).then();
		

	}

}
