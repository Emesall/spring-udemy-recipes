package com.emesall.recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.converters.IngredientCommandToIngredient;
import com.emesall.recipes.converters.IngredientToIngredientCommand;
import com.emesall.recipes.model.Ingredient;
import com.emesall.recipes.repositories.IngredientRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Autowired
	public IngredientServiceImpl(IngredientRepository ingredientRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		super();
		this.ingredientRepository = ingredientRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public Ingredient findById(String id) {

		return ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException("No ingredient found"));
	}

	@Override
	public IngredientCommand findCommandById(String id,String recipeId) {
		IngredientCommand command=ingredientToIngredientCommand.convert(findById(id));
		command.setRecipeId(recipeId);
		return command;
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Ingredient detachedIngredient= ingredientCommandToIngredient.convert(command);
		
		Ingredient savedIngredient=ingredientRepository.save(detachedIngredient);
		
		log.debug("Saved IngredientId:" + savedIngredient.getId());
		IngredientCommand savedCommand=ingredientToIngredientCommand.convert(savedIngredient);
		savedCommand.setRecipeId(command.getRecipeId());
		return savedCommand;
	}

	@Override
	public void deleteIngredientById(String id) {
		ingredientRepository.deleteById(id);
		
	}

}
