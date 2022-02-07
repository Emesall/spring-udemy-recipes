package com.emesall.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.recipes.repositories.CategoryRepository;

@Controller
public class IndexController {

	CategoryRepository categoryRepository;
	
	
	@Autowired
	public IndexController(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}



	@RequestMapping({"","/","/index"})
	public String getIndexPage() {
		
		categoryRepository.findByName("kategoria2").ifPresent(category ->System.out.println(category.getId()));
		

		
		return "index";
	}
}
