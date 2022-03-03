package com.emesall.recipes.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")

public class Ingredient {

	private String id;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasure uom;
	private Recipe recipe;

	public Ingredient(String description, BigDecimal amount) {
		super();
		this.description = description;
		this.amount = amount;
	}

}
