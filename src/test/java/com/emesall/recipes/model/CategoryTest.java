package com.emesall.recipes.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

	private Category cat;
	@BeforeEach
	void setUp() throws Exception {
		cat=new Category();
	}

	@Test
	void testGetId() {
		Long num=4L;
		cat.setId(num);
		assertEquals(num,4L);
	
	}

}
