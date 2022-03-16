package com.emesall.recipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.services.RecipeService;

@Configuration
public class WebConfig {
	   @Bean
	    RouterFunction<?> routes(RecipeService recipeService){
	        return RouterFunctions.route(GET("/api/recipes"),
	                serverRequest -> ServerResponse
	                                    .ok()
	                                    .contentType(MediaType.APPLICATION_JSON)
	                                    .body(recipeService.getRecipes(), Recipe.class));

	    }
}
