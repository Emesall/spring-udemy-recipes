package com.emesall.recipes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;

class RecipeListServiceTest {

	 
	ListService<Recipe> listService;

	@Mock
	RecipeRepository recipeRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		listService=new RecipeListService(recipeRepository);
	}

	@Test
	void testGetRecipes() {
		Set<Recipe> recipesFake=new HashSet<Recipe>();
		recipesFake.add(new Recipe());
		when(recipeRepository.findAll()).thenReturn(recipesFake);
		
		Set<Recipe> recipes=listService.getRecipes();
		
		assertEquals(1, recipes.size());
		verify(recipeRepository,times(1)).findAll();
		
	}

}
