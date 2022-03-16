package com.emesall.recipes.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.converters.IngredientCommandToIngredient;
import com.emesall.recipes.converters.IngredientToIngredientCommand;
import com.emesall.recipes.exceptions.NotFoundException;
import com.emesall.recipes.model.Ingredient;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Mono;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeReactiveRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureService unitOfMeasureService;

	@Autowired
	public IngredientServiceImpl(RecipeReactiveRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@Override
	public Mono<Ingredient> findById(String recipeId, String id) {

		Mono<Ingredient> ingredient = recipeRepository.findById(recipeId)
				.flatMapIterable(rec -> rec.getIngredients())
				.filter(ing -> ing.getId().equals(id))
				.switchIfEmpty(Mono.error(new NotFoundException("Ingredient not found: ")))
				.single();
				

		return ingredient;

	}

	@Override
	public Mono<IngredientCommand> findCommandById(String id, String recipeId) {

		return findById(recipeId, id).map(ing -> {
			IngredientCommand ingCommand = ingredientToIngredientCommand.convert(ing);
			ingCommand.setRecipeId(recipeId);
			return ingCommand;
		});

	}

	
	@Override
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {

		// find recipe
		
		Recipe recipe = recipeRepository
				.findById(command.getRecipeId())
				.switchIfEmpty(Mono.error(new RuntimeException("Recipe not found: " + command.getRecipeId())))
				.toProcessor().block();
				

		// find ingredient in recipe
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();
		// if ingredient is already in recipe
		if (ingredientOptional.isPresent()) {
			Ingredient ingredientFound = ingredientOptional.get();
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUom(unitOfMeasureService.findById(command.getUom().getId()).toProcessor().block());
					
		}
		// no ingredient in recipe
		else {
			command.setId(UUID.randomUUID().toString());
			Ingredient ingredient = ingredientCommandToIngredient.convert(command);
			ingredient.setUom(unitOfMeasureService.findById(command.getUom().getId()).toProcessor().block());
			recipe.addIngredient(ingredient);
		}

		Recipe savedRecipe = recipeRepository.save(recipe).toProcessor().block();

		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst();
		
		
		
		return Mono.just(ingredientToIngredientCommand.convert(savedIngredientOptional.get()));
		
		
	
		
		
	}

	@Override
	public Mono<Void> deleteIngredientById(String recipeId, String id) {

		return recipeRepository.findById(recipeId).map(recipe -> {
			recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(id));
			return recipe;
		}).flatMap(recipeRepository::save).then();

	}

}
