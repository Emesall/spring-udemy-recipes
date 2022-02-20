package com.emesall.recipes.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.exceptions.NotFoundException;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.services.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

	RecipeController recipeController;
	@Mock
	RecipeServiceImpl recipeService;
	@Mock
	Model model;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test
	void MockMvcTest() throws Exception {

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
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipes/1/show")).andExpect(status().isOk()).andExpect(view().name("recipes/show"));

	}
	
	@Test
	void testShowByIdNotFound() throws Exception {

		when(recipeService.findById(anyLong())).thenThrow(new NotFoundException("Recipe not found"));

		mockMvc.perform(get("/recipes/1/show")).andExpect(status().isNotFound()).andExpect(view().name("404error"));

	}

	@Test
	void testNewRecipe() throws Exception {

		mockMvc.perform(get("/recipes/new")).andExpect(status().isOk()).andExpect(view().name("recipes/recipeForm"))
				.andExpect(model().attributeExists("recipe"));
		;
	}

	@Test
	void testSaveRecipe() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(post("/recipes").contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipes/2/show"));
	}

	@Test
	void testUpdate() throws Exception {
		RecipeCommand recipe = new RecipeCommand();
		recipe.setId(1L);
		when(recipeService.findCommandById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipes/1/update")).andExpect(status().isOk())
				.andExpect(view().name("recipes/recipeForm")).andExpect(model().attributeExists("recipe"));
		;

	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(get("/recipes/1/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipes"));

		verify(recipeService, times(1)).deleteRecipeById(anyLong());
	}

}
