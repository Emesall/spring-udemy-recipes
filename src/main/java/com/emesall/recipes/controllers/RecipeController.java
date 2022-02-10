package com.emesall.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.recipes.services.RecipeListService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class RecipeController {

	private final RecipeListService recipeListService;
	
	@Autowired
	public RecipeController(RecipeListService recipeListService) {
		super();
		this.recipeListService = recipeListService;
	}

	@RequestMapping("/recipes")
	public String getRecipeList(Model model) {
		log.debug("Loading recipes page");
		model.addAttribute("recipes", recipeListService.getRecipes());
		
		return "recipes/list";
	}
}
