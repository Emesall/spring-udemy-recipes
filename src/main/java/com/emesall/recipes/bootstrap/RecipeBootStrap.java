package com.emesall.recipes.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emesall.recipes.model.Difficulty;
import com.emesall.recipes.model.Ingredient;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.model.UnitOfMeasure;
import com.emesall.recipes.repositories.CategoryRepository;
import com.emesall.recipes.repositories.RecipeRepository;
import com.emesall.recipes.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	@Autowired
	public RecipeBootStrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	private List<Recipe> getRecipes() {

		List<Recipe> recipes = new ArrayList<Recipe>();
		Ingredient avocado = new Ingredient("Avocado", new BigDecimal(2));
		Ingredient salt = new Ingredient("Salt", new BigDecimal(0.25));
		salt.setUom(getUnitOfMeasure("teaspoon"));
		Ingredient lime = new Ingredient("Lime", new BigDecimal(1));
		lime.setUom(getUnitOfMeasure("tablespoon"));
		Ingredient onion = new Ingredient("Onion", new BigDecimal(2));
		onion.setUom(getUnitOfMeasure("tablespoon"));
		Ingredient chili = new Ingredient("Chili", new BigDecimal(1));
		Ingredient cilantro = new Ingredient("Cilantro", new BigDecimal(2));
		cilantro.setUom(getUnitOfMeasure("tablespoon"));
		Ingredient pepper = new Ingredient("Pepper", new BigDecimal(1));
		pepper.setUom(getUnitOfMeasure("pinch"));
		Ingredient tomato = new Ingredient("Tomato", new BigDecimal(0.5));

		Recipe recipe1 = new Recipe("Guacamole");
		recipe1.setCookTime(10);
		recipe1.setDifficulty(Difficulty.MEDIUM);
		recipe1.setPrepTime(10);
		recipe1.setUrl("/guacamole");
		recipe1.setServings(4);
		recipe1.addIngredient(avocado);
		recipe1.addIngredient(salt);
		recipe1.addIngredient(lime);
		recipe1.addIngredient(onion);
		recipe1.addIngredient(chili);
		recipe1.addIngredient(cilantro);
		recipe1.addIngredient(pepper);
		recipe1.addIngredient(tomato);

		Recipe recipe2 = new Recipe("Recipe2");
		recipe2.setCookTime(20);
		recipe2.setDifficulty(Difficulty.HARD);
		recipe2.setPrepTime(20);
		recipe2.setUrl("/pasta");
		recipe2.setServings(4);

		recipe1.addCategory(categoryRepository.findByName("Mexican").get());
		recipe2.addCategory(categoryRepository.findByName("American").get());
		recipes.add(recipe1);
		recipes.add(recipe2);

		return recipes;
	}

	private UnitOfMeasure getUnitOfMeasure(String description) {
		return unitOfMeasureRepository.findByDescription(description)
				.orElseThrow(() -> new RuntimeException("Unit of measure '" + description + "' not found"));
	}

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug("Starting boot app");
		recipeRepository.saveAll(getRecipes());
		Recipe recipe1 = new Recipe("Guac");
		recipe1.setId(1L);
		//recipeRepository.save(recipe1);
		//recipeRepository.deleteById(1L);
		
		
		
		
	}

}
