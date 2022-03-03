package com.emesall.recipes.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")

public class Notes {

	private String id;

	private Recipe recipe;

	private String recipeNotes;

}
