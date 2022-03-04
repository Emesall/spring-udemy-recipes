package com.emesall.recipes.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data

public class Notes {

	@Id
	private String id;

	private String recipeNotes;

}
