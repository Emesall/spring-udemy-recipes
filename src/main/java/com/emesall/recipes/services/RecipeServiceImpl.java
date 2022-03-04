package com.emesall.recipes.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.converters.RecipeCommandToRecipe;
import com.emesall.recipes.converters.RecipeToRecipeCommand;
import com.emesall.recipes.exceptions.NotFoundException;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {

		Set<Recipe> recipes = new HashSet<Recipe>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

		return recipes;
	}

	@Override
	public Recipe findById(String id) {
		return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("No recipe found for ID: "+id));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		

		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		RecipeCommand savedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe);

		// enhance command object with id value
		if (savedRecipeCommand.getIngredients() != null && savedRecipeCommand.getIngredients().size() > 0) {
			savedRecipeCommand.getIngredients().forEach(rc -> {
				rc.setRecipeId(savedRecipeCommand.getId());
			});
		}
		log.debug("Saved RecipeId:" + savedRecipe.getId());
		return savedRecipeCommand;
	}
	
	@Override
	public RecipeCommand findCommandById(String id) {
		RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id));

		// enhance command object with id value
		if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
			recipeCommand.getIngredients().forEach(rc -> {
				rc.setRecipeId(recipeCommand.getId());
			});
		}
		return recipeCommand;
	}

	@Override
	public void deleteRecipeById(String id) {
	
		recipeRepository.deleteById(id);

	}
	
	

}
