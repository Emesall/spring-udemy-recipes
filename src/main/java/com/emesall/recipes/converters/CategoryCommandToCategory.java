package com.emesall.recipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.emesall.recipes.commands.CategoryCommand;
import com.emesall.recipes.model.Category;

import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{

	
	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		
		if(source==null) {
			return null;
		}
		
		Category category=new Category();
		category.setId(source.getId());
		category.setName(source.getName());
		return category;
		
	}

	
	
	
}
