package com.emesall.recipes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.emesall.recipes.converters.RecipeCommandToRecipe;
import com.emesall.recipes.converters.RecipeToRecipeCommand;
import com.emesall.recipes.exceptions.NotFoundException;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class RecipeServiceImplTest {

	RecipeService recipeService;

	@Mock
	RecipeReactiveRepository recipeRepository;
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	void testGetRecipes() {
		List<Recipe> recipesFake = new ArrayList<Recipe>();
		recipesFake.add(new Recipe());
		when(recipeRepository.findAll()).thenReturn(Flux.fromIterable(recipesFake));

		List<Recipe> recipes = recipeService.getRecipes().collectList().block();

		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();

	}

	@Test
	void testFindById() {
		Recipe recipe = new Recipe();
		String id = "1";
		recipe.setId(id);
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		Recipe rec = recipeService.findById(id).block();
		assertNotNull(rec);
		assertEquals("1", rec.getId());
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, never()).findAll();

	}

	

	@Test
	void testDeleteById() {
		//given
		String id = "1";
		//when
		when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
		recipeService.deleteRecipeById(id);
		//then
		verify(recipeRepository, times(1)).deleteById(anyString());

	}

}
