package com.emesall.recipes.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.emesall.recipes.converters.RecipeCommandToRecipe;
import com.emesall.recipes.converters.RecipeToRecipeCommand;
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
		recipeService=new RecipeServiceImpl(recipeRepository,recipeCommandToRecipe,recipeToRecipeCommand);
	}

	@Test
	void testGetRecipes() {
		Set<Recipe> recipesFake=new HashSet<Recipe>();
		recipesFake.add(new Recipe());
		when(recipeRepository.findAll()).thenReturn(recipesFake);
		
		Set<Recipe> recipes=recipeService.getRecipes();
		
		assertEquals(1, recipes.size());
		verify(recipeRepository,times(1)).findAll();
		
	}
	@Test
	void testFindById() {
		Recipe recipe=new Recipe();
		Long id=1L;
		recipe.setId(id);
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		
		Recipe rec=recipeService.findById(id);
		assertNotNull(rec);
		assertEquals(1L,rec.getId());
		verify(recipeRepository,times(1)).findById(anyLong());
		verify(recipeRepository,never()).findAll();
		
	}
	
	
	
	

}
