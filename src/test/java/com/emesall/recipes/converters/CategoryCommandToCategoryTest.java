package com.emesall.recipes.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.emesall.recipes.commands.CategoryCommand;
import com.emesall.recipes.model.Category;

class CategoryCommandToCategoryTest {

	public static final String ID_VALUE = "1";
	public static final String DESCRIPTION = "description";
	CategoryCommandToCategory conventer;

	@BeforeEach
	void setUp() throws Exception {
		conventer = new CategoryCommandToCategory();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(conventer.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(conventer.convert(new CategoryCommand()));
	}

	@Test
	void testConvert() {
		CategoryCommand cat1 = new CategoryCommand();
		cat1.setId(ID_VALUE);
		cat1.setName(DESCRIPTION);

		Category category = conventer.convert(cat1);

		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getName());

	}

}
