package com.emesall.recipes.repositories.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.emesall.recipes.model.Category;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepositoryIT {

	@Autowired
	CategoryReactiveRepository categoryRepository;

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void testSaveUom() {
		Category category=new Category();
		category.setName("test");
		categoryRepository.save(category).block();
		
		assertEquals(1L, categoryRepository.count().block());
		
	}

}
