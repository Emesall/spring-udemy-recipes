package com.emesall.recipes.services;

import java.util.Set;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.model.Recipe;

public interface RecipeService
{
	Set<Recipe> getRecipes();
	Recipe findById(String id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(String id);
	void deleteRecipeById(String id);
}
