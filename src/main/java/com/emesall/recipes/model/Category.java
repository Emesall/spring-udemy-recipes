package com.emesall.recipes.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "recipes")
@ToString(exclude = "recipes")

public class Category {

	
	private String Id;
	private String name;
	private Set<Recipe> recipes = new HashSet<Recipe>();

}
