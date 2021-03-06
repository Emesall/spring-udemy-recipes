package com.emesall.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.commands.UnitOfMeasureCommand;
import com.emesall.recipes.services.IngredientService;
import com.emesall.recipes.services.RecipeService;
import com.emesall.recipes.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

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
	public String getIngredientsList(@PathVariable Long recipeId, Model model) {
		log.debug("List with ingredients ");
		model.addAttribute("recipe", recipeService.findById(recipeId));

		return "recipes/ingredients/list";
	}

	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/show")
	public String showById(Model model, @PathVariable Long ingredientId) {
		log.debug("Ingredient show page with ID: " + ingredientId);

		model.addAttribute("ingredient", ingredientService.findById(ingredientId));
		return "recipes/ingredients/show";
	}

	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/update")
	public String getUpdateIngredient(@PathVariable Long ingredientId, Model model) {
		log.debug("Ingredient update page with ID: " + ingredientId);

		model.addAttribute("ingredient", ingredientService.findCommandById(ingredientId));
		model.addAttribute("uomList",unitOfMeasureService.listUoM());
		return "recipes/ingredients/ingredientForm";
	}
	
	@RequestMapping("/recipes/{recipeId}/ingredients/new")
	public String getNewIngredient(@PathVariable Long recipeId, Model model) {
		log.debug("Adding new ingredient to recipe: " + recipeId);

		//make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        
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
	public String saveIngredient(@ModelAttribute IngredientCommand ingredientCommand ) {
		log.debug("Saving/updating ingredient ..");
		IngredientCommand savedCommand=ingredientService.saveIngredientCommand(ingredientCommand);
		
		
		return "redirect:/recipes/"+savedCommand.getRecipeId()+"/ingredients";
		
	}
	
	@RequestMapping("/recipes/{recipeId}/ingredients/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
		log.debug("Ingredient deleting: " + ingredientId);

		ingredientService.deleteIngredientById(ingredientId);
		
		return "redirect:/recipes/"+recipeId+"/ingredients/";
	}
	
	
}
