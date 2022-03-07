package com.emesall.recipes.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.converters.IngredientCommandToIngredient;
import com.emesall.recipes.converters.IngredientToIngredientCommand;
import com.emesall.recipes.model.Ingredient;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;
import com.emesall.recipes.repositories.UnitOfMeasureRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	@Autowired
	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public Ingredient findById(String recipeId, String id) {

		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("No recipe found"));
		Ingredient ingredient = recipe.getIngredients().stream().filter(ing -> ing.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("No ingredient found"));
		return ingredient;
	}

	@Override
	public IngredientCommand findCommandById(String id, String recipeId) {
		IngredientCommand command = ingredientToIngredientCommand.convert(findById(recipeId, id));
		command.setRecipeId(recipeId);
		return command;
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {

		// find recipe
		Recipe recipe = recipeRepository.findById(command.getRecipeId())
				.orElseThrow(() -> new RuntimeException("No recipe found"));
		// find ingredient in recipe
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();
		// if ingredient is already in recipe
		if (ingredientOptional.isPresent()) {
			Ingredient ingredientFound = ingredientOptional.get();
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
					.orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
		}
		// no ingredient in recipe
		else {
			command.setId(UUID.randomUUID().toString());
			Ingredient ingredient = ingredientCommandToIngredient.convert(command);
			System.out.println(command.getId());
			recipe.addIngredient(ingredient);
		}

		Recipe savedRecipe = recipeRepository.save(recipe);

		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst();

		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

	@Override
	public void deleteIngredientById(String recipeId, String id) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("No recipe found"));
		Ingredient ingredient = recipe.getIngredients().stream().filter(ing -> ing.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("No ingredient found"));
		recipe.getIngredients().remove(ingredient);
		recipeRepository.save(recipe);

	}

}
