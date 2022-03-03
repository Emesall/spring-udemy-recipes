package com.emesall.recipes.converters;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.emesall.recipes.commands.CategoryCommand;
import com.emesall.recipes.model.Category;

class CategoryToCategoryCommandTest {

	public static final String ID="1";
	public static final String DESCRIPTION="desc";
	CategoryToCategoryCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter=new CategoryToCategoryCommand();
	}
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Category()));
	}
	@Test
	void test() {
		//given
		Category category=new Category();
		category.setId(ID);
		category.setName(DESCRIPTION);
		//when
		CategoryCommand categoryCommand=converter.convert(category);
		//then
		assertEquals(ID, categoryCommand.getId());
		assertEquals(DESCRIPTION, category.getName());
	}

}
