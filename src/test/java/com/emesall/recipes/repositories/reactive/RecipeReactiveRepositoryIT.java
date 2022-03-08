package com.emesall.recipes.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.emesall.recipes.model.Recipe;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class RecipeReactiveRepositoryIT {

	@Autowired
	RecipeReactiveRepository recipeRepository;

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void testSaveUom() {
		Recipe recipe=new Recipe();
		recipe.setDescription("test");
		recipeRepository.save(recipe).block();
		
		assertEquals(1L, recipeRepository.count().block());
		
	}

	

}
