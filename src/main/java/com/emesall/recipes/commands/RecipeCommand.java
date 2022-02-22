package com.emesall.recipes.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.URL;

import com.emesall.recipes.model.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@Min(1)
	private Integer prepTime;
	
	@Min(1)
	private Integer cookTime;
	
	@Min(1)
    @Max(100)
	private Integer servings;
	private String source;
	
	@URL
	private String url;
	
	@NotBlank
	private String directions;
	
	private Difficulty difficulty;
	private NotesCommand notes;
	private Set<IngredientCommand> ingredients = new HashSet<IngredientCommand>();
	private Set<CategoryCommand> categories = new HashSet<CategoryCommand>();
}
