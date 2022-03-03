package com.emesall.recipes.services;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.model.Ingredient;

public interface IngredientService {

	Ingredient findById(String id);
	IngredientCommand findCommandById(String id);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	void deleteIngredientById(String id);
}
