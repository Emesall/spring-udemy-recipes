package com.emesall.recipes.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;
	@Lob
	private Byte[] image;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe")
	private Notes notes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();

	@ManyToMany
	@JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<Category>();

	public Recipe(String description) {
		super();
		this.description = description;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		ingredient.setRecipe(this);
	}

	public void addCategory(Category category) {
		category.getRecipes().add(this);
		this.categories.add(category);

	}

}
