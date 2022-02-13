package com.emesall.recipes.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.model.Recipe;

import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
	private final CategoryToCategoryCommand categoryConveter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final NotesToNotesCommand notesConverter;

	@Autowired
	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter,
			IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
		super();
		this.categoryConveter = categoryConveter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}

		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
					.forEach(category -> recipeCommand.getCategories().add(categoryConveter.convert(category)));
		}

		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
					.forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
		}

		return recipeCommand;
	}
}
