package com.emesall.recipes.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.model.Ingredient;

import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	
	@Autowired
	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomconverter) {
		super();
		this.uomConverter = uomconverter;
	}


	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		
		if(source==null) {
			return null;
		}
		
		final Ingredient ingredient=new Ingredient();
		/*if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            recipe.addIngredient(ingredient);
        }*/
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setId(source.getId());
		ingredient.setUom(uomConverter.convert(source.getUom()));
		return ingredient;
	}

	
}
