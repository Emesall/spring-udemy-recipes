package com.emesall.recipes.controllers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

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
		
		assertEquals("recipes/list",recipeController.getRecipeList(model) );
		verify(recipeListService,times(1)).getRecipes();
		verify(model,times(1)).addAttribute(eq("recipes"),anySet());
	}

}
