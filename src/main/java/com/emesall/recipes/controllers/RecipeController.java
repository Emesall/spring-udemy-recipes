package com.emesall.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.recipes.services.RecipeServiceImpl;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class RecipeController {

	private final RecipeServiceImpl recipeService;
	
	@Autowired
	public RecipeController(RecipeServiceImpl recipeListService) {
		super();
		this.recipeService = recipeListService;
	}

	@RequestMapping("/recipes")
	public String getRecipeList(Model model) {
		log.debug("Loading recipes page");
		model.addAttribute("recipes", recipeService.getRecipes());
		
		return "recipes/list";
	}
	@RequestMapping("/recipes/show/{id}")
	public String showById(@PathVariable String id,Model model) {
		log.debug("Loading recipe page with ID: "+id);
		model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));
		
		return "recipes/show";
	}
}
