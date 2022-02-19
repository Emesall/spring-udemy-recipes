package com.emesall.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.emesall.recipes.services.ImageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeImageController {

	
	private final ImageService imageService;
	
	

	public RecipeImageController(ImageService imageService) {
		super();
		this.imageService = imageService;
	}



	@GetMapping("recipes/{recipeId}/image")
	public String showUploadForm(@PathVariable Long recipeId,Model model) {
		log.debug("Showing image upload page");
		model.addAttribute("recipeId", recipeId);
		
		return "recipes/imageUploadForm";
	}
	
	
	
	@PostMapping("recipes/{recipeId}/image")
	public String handleImageUpload(@PathVariable Long recipeId,@RequestParam("imagefile") MultipartFile file) {
		log.debug("Request from view to controller to save image..");
		imageService.saveImageFile(recipeId, file);
		
		return "redirect:/recipes/{recipeId}/show";
	}
	
}
