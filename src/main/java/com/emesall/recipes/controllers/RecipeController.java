package com.emesall.recipes.controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.services.RecipeService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class RecipeController {

	private final RecipeService recipeService;

	@Autowired
	public RecipeController(RecipeService recipeListService) {
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
	public String showById(@PathVariable String id, Model model) {
		log.debug("Loading recipe page with ID: " + id);
		model.addAttribute("recipe", recipeService.findById(id));

		return "recipes/show";
	}

	@RequestMapping("/recipes/new")
	public String newRecipe(Model model) {
		log.debug("Opening page to add new recipe");
		model.addAttribute("recipe", new RecipeCommand());
		return "recipes/recipeForm";
	}

	@RequestMapping("/recipes/{id}/update")
	public String getUpdateRecipe(@PathVariable String id, Model model) {
		log.debug("Opening page update Recipe");
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipes/recipeForm";
	}

	@PostMapping("recipes")
	public Mono<String> saveOrUpdate(@Valid @ModelAttribute("recipe") Mono<RecipeCommand> recipeCommand) {
		
		return recipeCommand
				.flatMap(recipeService::saveRecipeCommand)
				.map(rec->"redirect:/recipes/" + rec.getId() + "/show")
				.doOnError(thr -> log.error("Error saving recipe"))
		        .onErrorResume(WebExchangeBindException.class, thr -> Mono.just("recipes/recipeform"));		
	
	}

	@RequestMapping("/recipes/{id}/delete")
	public Mono<String> deleteRecipe(@PathVariable String id, Model model) {
		log.debug("Deleting recipe with ID: " + id);
		return recipeService.deleteRecipeById(id)
				.thenReturn("redirect:/recipes");
				
		
		
	}
	

}
