package com.emesall.recipes.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ImageServiceIT {
	@Autowired
	RecipeService recipeService;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	ImageService imageService;

	@Transactional
	@Test
	public void testSaveandGetImage() throws Exception {
		// given
		String id = "1";
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Emesall".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(id);
		recipe.setImage(multipartFile.getBytes());
		recipeRepository.save(recipe);

		// when

		imageService.saveImageFile(id, multipartFile);
		Recipe rec=recipeRepository.findById(id).orElseThrow(()->new RuntimeException("No recipe found"));
		//byte[] result = imageService.getImage(id);

		// then
		assertEquals(recipe.getImage().length, rec.getImage().length);
		assertArrayEquals(recipe.getImage(), rec.getImage());
		

	}
}
