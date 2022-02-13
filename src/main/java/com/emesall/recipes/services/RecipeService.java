package com.emesall.recipes.services;

import java.util.Set;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.model.Recipe;

public interface RecipeService
{
	Set<Recipe> getRecipes();
	Recipe findById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	RecipeCommand findCommandById(Long id);
	void deleteRecipeById(Long id);
}
