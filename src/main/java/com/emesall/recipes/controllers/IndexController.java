package com.emesall.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emesall.recipes.repositories.CategoryRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
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
		log.debug("Loading index page");
		
		return "index";
	}
}
