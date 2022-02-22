package com.emesall.recipes.controllers;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.emesall.recipes.services.ImageService;

@ExtendWith(MockitoExtension.class)
class RecipeImageControllerTest {

	MockMvc mockMvc;
	RecipeImageController imageController;
	@Mock
	ImageService imageService;

	@BeforeEach
	void setUp() throws Exception {
		imageController = new RecipeImageController(imageService);
		mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(new ExceptionHandlingController()).build();
	}

	@Test
	void testShowUploadForm() throws Exception {

		mockMvc.perform(get("/recipes/1/image")).andExpect(status().isOk())
				.andExpect(view().name("recipes/imageUploadForm")).andExpect(model().attributeExists("recipeId"));
	}

	@Test
	void testHandleImageUpload() throws Exception {
		MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Emesall".getBytes());
		mockMvc.perform(multipart("/recipes/1/image").file(multipartFile))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipes/{recipeId}/show"));
		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}

}
