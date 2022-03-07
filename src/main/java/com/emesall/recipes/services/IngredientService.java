package com.emesall.recipes.services;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.model.Ingredient;

public interface IngredientService {

	Ingredient findById(String recipeId,String id);
	IngredientCommand findCommandById(String id,String recipeId);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	void deleteIngredientById(String recipeId,String id);
}
