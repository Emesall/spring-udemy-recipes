package com.emesall.recipes.controllers;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.emesall.recipes.commands.RecipeCommand;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.services.RecipeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RecipeController.class)
class RecipeControllerTest {

	RecipeController recipeController;
	@MockBean
	RecipeService recipeService;
	@Autowired
	WebTestClient webTestClient;


	@BeforeEach
	void setUp() throws Exception {
		recipeController = new RecipeController(recipeService);
	}


	@Test
	void testGetRecipeList() {
		// given
		Recipe recipe = new Recipe();
		recipe.setDescription("test");
		// when
		when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));
		// then
		webTestClient.get()
		.uri("/recipes")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.value(s->s.equals("recipes/list"))
		.consumeWith(response -> {
			
			String responseBody = Objects.requireNonNull(response.getResponseBody());
			assertTrue(responseBody.contains("test"));
		});
		verify(recipeService, times(1)).getRecipes();
	}

	@Test
	void testShowById() throws Exception {
		// given
				Recipe recipe = new Recipe();
				recipe.setDescription("test");
				recipe.setId("1");
				// when
				when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));
				// then
				webTestClient.get()
				.uri("/recipes/1/show")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.value(s->s.equals("recipes/show"))
				.consumeWith(response -> {
					
					String responseBody = Objects.requireNonNull(response.getResponseBody());
					assertTrue(responseBody.contains("test"));
					
				});
				
				verify(recipeService, times(1)).findById(anyString());

	}

	@Test
	void testShowByIdNotFound() throws Exception {

	

	}

	@Test
	void testNewRecipe() throws Exception {
		
		webTestClient.get()
		.uri("/recipes/new")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.value(s->s.equals("recipes/recipeForm"));
								
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand rec=new RecipeCommand();
		rec.setId("1");
		when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(Mono.just(rec));
		
		webTestClient.post()
		.uri("/recipes")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.attribute("id", "")
		.attribute("description", "dsds")
		.attribute("directions", "some")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.value(s->s.equals("redirect:/recipes/1/show"));
		
	}

	
	@Test
	void testUpdate() throws Exception {
		// given
		RecipeCommand recipe = new RecipeCommand();
		recipe.setDescription("test");
		recipe.setId("1");
		
		// when
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipe));
		// then
		webTestClient.get()
		.uri("/recipes/1/update")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class)
		.value(s->s.equals("recipes/recipeForm"))
		.consumeWith(response -> {
			
			String responseBody = Objects.requireNonNull(response.getResponseBody());
			assertTrue(responseBody.contains("test"));
			
		});
		
		verify(recipeService, times(1)).findCommandById(anyString());
	}

	@Test
	void testDelete() throws Exception {			
		// when
		when(recipeService.deleteRecipeById(anyString())).thenReturn(Mono.empty());
		// then
		webTestClient.get()
		.uri("/recipes/1/delete")
		.exchange()
		.expectStatus().is3xxRedirection();
								
		verify(recipeService, times(1)).deleteRecipeById(anyString());			
	}

}
