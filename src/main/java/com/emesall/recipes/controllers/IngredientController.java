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

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.commands.UnitOfMeasureCommand;
import com.emesall.recipes.services.IngredientService;
import com.emesall.recipes.services.RecipeService;
import com.emesall.recipes.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	@Autowired
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService=unitOfMeasureService;
	}

	@RequestMapping("/recipes/{recipeId}/ingredients")
	public String getIngredientsList(@PathVariable String recipeId, Model model) {
		log.debug("List with ingredients ");
		model.addAttribute("recipe", recipeService.findById(recipeId));

		return "recipes/ingredients/list";
	}

	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/show")
	public String showById(Model model,@PathVariable String recipeId, @PathVariable String ingredientId) {
		log.debug("Ingredient show page with ID: " + ingredientId);

		model.addAttribute("ingredient", ingredientService.findById(recipeId,ingredientId));
		return "recipes/ingredients/show";
	}

	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/update")
	public String getUpdateIngredient(@PathVariable String recipeId,@PathVariable String ingredientId, Model model) {
		log.debug("Ingredient update page with ID: " + ingredientId);

		model.addAttribute("ingredient", ingredientService.findCommandById(ingredientId,recipeId));
		model.addAttribute("uomList",unitOfMeasureService.listUoM());
		return "recipes/ingredients/ingredientForm";
	}
	
	@RequestMapping("/recipes/{recipeId}/ingredients/new")
	public String getNewIngredient(@PathVariable String recipeId, Model model) {
		log.debug("Adding new ingredient to recipe: " + recipeId);


        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("uomList",unitOfMeasureService.listUoM());
		return "recipes/ingredients/ingredientForm";
	}
	
	
	@PostMapping("/recipes/{recipeId}/ingredients")
	public Mono<String> saveIngredient(@Valid @ModelAttribute("ingredient") Mono<IngredientCommand> ingredientCommand,@PathVariable String recipeId ) {
		log.debug("Saving/updating ingredient ..");
		return ingredientCommand
				.flatMap(ingredientService::saveIngredientCommand)
				.map(ingr->"redirect:/recipes/"+recipeId+"/ingredients")
				.doOnError(thr -> log.error("Error saving ingredient"))
				.onErrorResume(WebExchangeBindException.class,thr->Mono.just("recipes/ingredients/ingredientForm"));
		
	}
	
	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/delete")
	public Mono<String> deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Ingredient deleting: " + ingredientId);

		return ingredientService.deleteIngredientById(recipeId,ingredientId)
				.thenReturn("redirect:/recipes/"+recipeId+"/ingredients/");
	
	}
	
	
}
