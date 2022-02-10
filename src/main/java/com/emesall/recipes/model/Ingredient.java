package com.emesall.recipes.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")
@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private BigDecimal amount;
	@OneToOne(fetch = FetchType.EAGER)
	private UnitOfMeasure uom;
	@ManyToOne
	private Recipe recipe;

	
	public Ingredient(String description, BigDecimal amount) {
		super();
		this.description = description;
		this.amount = amount;
	}

	
}
