package com.emesall.recipes.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
public class Recipe {

	@Id
	private String id;

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Difficulty difficulty;
	private byte[] image;

	private Notes notes;
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	@DBRef
	private Set<Category> categories = new HashSet<Category>();

	public Recipe(String description) {
		super();
		this.description = description;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void addCategory(Category category) {
		
		this.categories.add(category);

	}

}
