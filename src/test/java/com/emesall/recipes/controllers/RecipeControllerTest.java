package com.emesall.recipes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.List;
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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ExceptionHandlingController()).build();

	}

	@Test
	void MockMvcTest() throws Exception {
		when(recipeService.getRecipes()).thenReturn(Flux.empty());
		mockMvc.perform(get("/recipes")).andExpect(status().isOk()).andExpect(view().name("recipes/list"));
	}

	@Test
	void testGetRecipeList() {

		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(new Recipe());
		when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

		ArgumentCaptor<List<Recipe>> captor = ArgumentCaptor.forClass(List.class);

		assertEquals("recipes/list", recipeController.getRecipeList(model));
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), captor.capture());

		List<Recipe> actual = captor.getValue();
		assertEquals(recipes.size(), actual.size());
	}

	@Test
	void testShowById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("2");
		when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));

		mockMvc.perform(get("/recipes/1/show")).andExpect(status().isOk()).andExpect(view().name("recipes/show"));

	}

	@Test
	void testShowByIdNotFound() throws Exception {

		when(recipeService.findById(anyString())).thenThrow(new NotFoundException("Recipe not found"));

		mockMvc.perform(get("/recipes/1/show")).andExpect(status().isNotFound()).andExpect(view().name("error"));

	}

	

	@Test
	void testNewRecipe() throws Exception {

		mockMvc.perform(get("/recipes/new")).andExpect(status().isOk()).andExpect(view().name("recipes/recipeForm"))
				.andExpect(model().attributeExists("recipe"));
		;
	}


	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");

		when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

		mockMvc.perform(post("/recipes").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
				.param("description", "some string").param("directions", "some directions"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipes/2/show"));
	}

	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");


		mockMvc.perform(post("/recipes").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "").param("cookTime", "0")

		).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"))
				.andExpect(view().name("recipes/recipeform"));
	}

	@Test
	void testUpdate() throws Exception {
		RecipeCommand recipe = new RecipeCommand();
		recipe.setId("1");
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipe));

		mockMvc.perform(get("/recipes/1/update")).andExpect(status().isOk())
				.andExpect(view().name("recipes/recipeForm")).andExpect(model().attributeExists("recipe"));
		;

	}

	@Test
	void testDelete() throws Exception {
		
		//when
		when(recipeService.deleteRecipeById("1")).thenReturn(Mono.empty());
		
		//then
		mockMvc.perform(get("/recipes/1/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipes"));

		verify(recipeService, times(1)).deleteRecipeById(anyString());
	}

}
