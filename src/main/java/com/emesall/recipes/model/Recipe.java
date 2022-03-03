package com.emesall.recipes.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Recipe {

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

	private Set<Category> categories = new HashSet<Category>();

	public Recipe(String description) {
		super();
		this.description = description;
	}

	public void addIngredient(Ingredient ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
	}

	public void addCategory(Category category) {
		category.getRecipes().add(this);
		this.categories.add(category);

	}

}
