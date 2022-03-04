package com.emesall.recipes.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "recipes")
@ToString(exclude = "recipes")
@Document
public class Category {

	@Id
	private String Id;
	private String name;
	@DBRef
	private Set<Recipe> recipes = new HashSet<Recipe>();

}
