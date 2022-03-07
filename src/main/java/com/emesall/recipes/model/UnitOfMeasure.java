package com.emesall.recipes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class UnitOfMeasure {

	@Id
	private String id;
	private String description;

}
