package com.emesall.recipes.converters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emesall.recipes.commands.IngredientCommand;
import com.emesall.recipes.model.Ingredient;
import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.model.UnitOfMeasure;

@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

	public static final String ID = "1";
	public static final String DESCRIPTION = "desc";
	public static final BigDecimal AMOUNT = new BigDecimal(2);
	public static final Recipe RECIPE = new Recipe();

	IngredientCommandToIngredient conventer;
	@Mock
	UnitOfMeasureCommandToUnitOfMeasure uomConventer;

	@BeforeEach
	void setUp() throws Exception {
		conventer = new IngredientCommandToIngredient(uomConventer);
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(conventer.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(conventer.convert(new IngredientCommand()));
	}

	@Test
	void test() {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID);
		ingredientCommand.setAmount(new BigDecimal(2));
		ingredientCommand.setDescription("desc");
		UnitOfMeasure uom=new UnitOfMeasure();
		uom.setDescription("de");
		uom.setId(ID);
		when(uomConventer.convert(any())).thenReturn(uom);
		// when
		Ingredient ingredient = conventer.convert(ingredientCommand);
		// then
		assertEquals(ID, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(1L, ingredient.getUom().getId());
		assertEquals("de", ingredient.getUom().getDescription());

	}

}
