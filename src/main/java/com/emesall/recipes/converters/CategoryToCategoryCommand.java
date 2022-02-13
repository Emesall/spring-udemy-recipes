package com.emesall.recipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.emesall.recipes.commands.CategoryCommand;
import com.emesall.recipes.model.Category;

import lombok.Synchronized;
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category source) {

		if (source == null) {
			return null;
		}

		final CategoryCommand catCommand = new CategoryCommand();
		catCommand.setId(source.getId());
		catCommand.setName(source.getName());
		return catCommand;

	}
}
