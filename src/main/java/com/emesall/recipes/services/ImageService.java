package com.emesall.recipes.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	void saveImageFile(String recipeId, MultipartFile file);
	byte[] getImage(String recipeId);
}
