package com.emesall.recipes.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	@Autowired
	public ImageServiceImpl(RecipeRepository recipeService) {

		this.recipeRepository = recipeService;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {

		try {

			byte[] image = file.getBytes();
			Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()->new RuntimeException("No recipe found"));
			recipe.setImage(image);
			recipeRepository.save(recipe);
			log.debug("Saved image");

		} catch (IOException exception) {
			log.debug("Problem with saving file");
			exception.printStackTrace();
		}

	}

	@Override
	public byte[] getImage(Long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()->new RuntimeException("No recipe found"));
		
		return recipe.getImage();
	}
	
	

}
