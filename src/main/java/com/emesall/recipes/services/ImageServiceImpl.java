package com.emesall.recipes.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.recipes.model.Recipe;
import com.emesall.recipes.repositories.reactive.RecipeReactiveRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeReactiveRepository recipeRepository;

	@Autowired
	public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {

		this.recipeRepository = recipeRepository;
	}

	@Override
	public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

		Mono<Recipe> rec=recipeRepository.findById(recipeId).map(recipe -> {
			try {
				byte[] image = file.getBytes();
				recipe.setImage(image);
				return recipe;
				
			} catch (IOException exception) {
				log.debug("Problem with saving file");
				exception.printStackTrace();
				throw new RuntimeException(exception);
			}
		});
		recipeRepository.save(rec.block()).block();
		
		log.debug("Saved image");
		return Mono.empty();
	}

	@Override
	public Mono<byte[]> getImage(String recipeId) {

		return recipeRepository.findById(recipeId).map(recipe -> recipe.getImage());

	}

}
