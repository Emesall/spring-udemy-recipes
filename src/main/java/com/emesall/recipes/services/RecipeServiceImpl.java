package com.emesall.recipes.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.converters.RecipeCommandToRecipe;
import com.emesall.recipes.converters.RecipeToRecipeCommand;
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
	public Recipe findById(Long id) {

		return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("No recipe found"));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		

		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		
		log.debug("Saved RecipeId:" + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}
	
	@Override
	public RecipeCommand findCommandById(Long id) {
		return recipeToRecipeCommand.convert(findById(id));
	}

	@Override
	public void deleteRecipeById(Long id) {
	
		recipeRepository.deleteById(id);

	}
	
	

}
