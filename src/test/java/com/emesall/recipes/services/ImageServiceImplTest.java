package com.emesall.recipes.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.reactive.RecipeReactiveRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

	@Mock
	RecipeReactiveRepository recipeRepository;

	ImageService imageService;

	@BeforeEach
	void setUp() throws Exception {

		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	void testSaveImageFile() throws IOException {
		String id = "1";
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Emesall".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(id);
		

		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.empty());

		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		// when
		imageService.saveImageFile(id, multipartFile);

		// then
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
		assertArrayEquals(multipartFile.getBytes(), savedRecipe.getImage());
	}

	@Test
	void testGetImage() throws IOException {
		// given
		String id = "1";
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Emesall".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(id);
		recipe.setImage(multipartFile.getBytes());
		


		// when
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		byte[] result=imageService.getImage(id).block();

		// then
		assertEquals(recipe.getImage(), result);
		assertArrayEquals(recipe.getImage(), result);

	}

}
