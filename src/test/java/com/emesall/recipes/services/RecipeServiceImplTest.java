package com.emesall.recipes.services;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.emesall.recipes.converters.RecipeCommandToRecipe;
import com.emesall.recipes.converters.RecipeToRecipeCommand;
import com.emesall.recipes.exceptions.NotFoundException;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;

class RecipeServiceImplTest {

	RecipeService recipeService;

	@Mock
	RecipeRepository recipeRepository;
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
		Set<Recipe> recipesFake = new HashSet<Recipe>();
		recipesFake.add(new Recipe());
		when(recipeRepository.findAll()).thenReturn(recipesFake);

		Set<Recipe> recipes = recipeService.getRecipes();

		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();

	}

	@Test
	void testFindById() {
		Recipe recipe = new Recipe();
		Long id = 1L;
		recipe.setId(id);
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

		Recipe rec = recipeService.findById(id);
		assertNotNull(rec);
		assertEquals(1L, rec.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();

	}

	@Test()
	void testFindByIdNotFound() throws Exception {
		// given
		Long id = 1L;

		// when
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
		// then

		NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> recipeService.findById(id),
				"Expected exception to throw an error. But it didn't");

		assertTrue(notFoundException.getMessage().contains("No recipe found"));

	}

	@Test
	void testDeleteById() {

		Long id = 1L;

		recipeService.deleteRecipeById(id);

		verify(recipeRepository, times(1)).deleteById(anyLong());

	}

}
