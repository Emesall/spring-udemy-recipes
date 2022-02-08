package com.emesall.recipes.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;

@Service
public class RecipeListService implements ListService<Recipe> {

	private final RecipeRepository recipeRepository;

	@Autowired
	public RecipeListService(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {

		Set<Recipe> recipes=new HashSet<Recipe>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		
		return recipes;
	}

}
