package com.emesall.recipes.controllers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.services.RecipeListService;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

	RecipeController recipeController;
	@Mock
	RecipeListService recipeListService;
	@Mock
	Model model;
	
	
	@BeforeEach
	void setUp() throws Exception {
		recipeController=new RecipeController(recipeListService);
		
	}

	@Test
	void testGetRecipeList() {
		
		Set<Recipe> recipes=new HashSet<Recipe>();
		recipes.add(new Recipe());
		when(recipeListService.getRecipes()).thenReturn(recipes);
		
		ArgumentCaptor<Set<Recipe>> captor=ArgumentCaptor.forClass(Set.class);
		
		assertEquals("recipes/list",recipeController.getRecipeList(model) );
		verify(recipeListService,times(1)).getRecipes();
		verify(model,times(1)).addAttribute(eq("recipes"),captor.capture());
		
		Set<Recipe> actual=captor.getValue();
		assertEquals(recipes.size(), actual.size());
	}

}
