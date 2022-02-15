package com.emesall.recipes.services;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.model.Ingredient;

public interface IngredientService {

	Ingredient findById(Long id);
	IngredientCommand findCommandById(Long id);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
}
