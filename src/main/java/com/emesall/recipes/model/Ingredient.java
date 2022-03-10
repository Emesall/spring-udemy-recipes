package com.emesall.recipes.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ingredient {

	private String id=UUID.randomUUID().toString();
	private String description;
	private BigDecimal amount;
	private UnitOfMeasure uom;

	public Ingredient(String description, BigDecimal amount) {
		super();
		this.description = description;
		this.amount = amount;
	}

}
