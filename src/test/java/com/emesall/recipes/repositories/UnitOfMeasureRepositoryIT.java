package com.emesall.recipes.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.emesall.recipes.bootstrap.RecipeBootStrap;
import com.emesall.recipes.model.UnitOfMeasure;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureRepositoryIT {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	@Autowired
	RecipeRepository recipeRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@BeforeEach
	void setUp() throws Exception {
		RecipeBootStrap bootStrap = new RecipeBootStrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
		bootStrap.onApplicationEvent(null);
	}

	@Test
	void testFindByDescription() {
		Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("pinch");
		assertEquals("pinch", unitOfMeasure.get().getDescription());
	}

}
