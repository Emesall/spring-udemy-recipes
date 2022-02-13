package com.emesall.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.recipes.commands.RecipeCommand;
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
	@RequestMapping("/recipes/{id}/show")
	public String showById(@PathVariable Long id,Model model) {
		log.debug("Loading recipe page with ID: "+id);
		model.addAttribute("recipe",recipeService.findById(id));
		
		return "recipes/show";
	}
	
	@RequestMapping("/recipes/new")
	public String newRecipe(Model model) {
		log.debug("Opening page to add new recipe");
		model.addAttribute("recipe", new RecipeCommand());
		return "recipes/recipeForm";
	}
	
	@RequestMapping("/recipes/{id}/update")
	public String updateRecipe(@PathVariable Long id,Model model) {
		log.debug("Opening page update Recipe");
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipes/recipeForm";
	}
	
	@PostMapping("recipes")
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		log.debug("Saving new recipe into the database..");
		RecipeCommand savedCommand= recipeService.saveRecipeCommand(recipeCommand);
		
		return "redirect:/recipes/"+savedCommand.getId()+"/show";
	}
	
}
