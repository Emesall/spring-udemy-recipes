package com.emesall.recipes.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.services.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

	RecipeController recipeController;
	@Mock
	RecipeServiceImpl recipeService;
	@Mock
	Model model;

	@BeforeEach
	void setUp() throws Exception {
		recipeController = new RecipeController(recipeService);

	}

	@Test
	void MockMvcTest() throws Exception {
		MockMvc mockMvc=MockMvcBuilders.standaloneSetup(recipeController).build();
		mockMvc.perform(get("/recipes")).andExpect(status().isOk()).andExpect(view().name("recipes/list"));
	}

	@Test
	void testGetRecipeList() {

		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(new Recipe());
		when(recipeService.getRecipes()).thenReturn(recipes);

		ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);

		assertEquals("recipes/list", recipeController.getRecipeList(model));
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), captor.capture());

		Set<Recipe> actual = captor.getValue();
		assertEquals(recipes.size(), actual.size());
	}
	
	@Test
	void testShowById() throws Exception {
		
		Recipe recipe=new Recipe();
		recipe.setId(1L);
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		mockMvc.perform(get("/recipes/show/1")).andExpect(status().isOk()).andExpect(view().name("recipes/show"));
		
		
		
	}

}
