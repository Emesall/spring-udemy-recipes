package com.emesall.recipes.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.emesall.recipes.model.Category;
import com.emesall.recipes.model.UnitOfMeasure;
import com.emesall.recipes.repositories.CategoryRepository;
import com.emesall.recipes.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({ "dev", "prod" })
public class BootStrapMySql implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public BootStrapMySql(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

		if (categoryRepository.count() == 0L) {
			log.debug("Loading Categories");
			loadCategories();
		}

		if (unitOfMeasureRepository.count() == 0L) {
			log.debug("Loading UOMs");
			loadUom();
		}
	}

	private void loadCategories() {
		Category cat1 = new Category();
		cat1.setName("American");
		categoryRepository.save(cat1);

		Category cat2 = new Category();
		cat2.setName("Italian");
		categoryRepository.save(cat2);

		Category cat3 = new Category();
		cat3.setName("Mexican");
		categoryRepository.save(cat3);

	}

	private void loadUom() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setDescription("teaspoon");
		unitOfMeasureRepository.save(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setDescription("tablespoon");
		unitOfMeasureRepository.save(uom2);

		UnitOfMeasure uom4 = new UnitOfMeasure();
		uom4.setDescription("Pinch");
		unitOfMeasureRepository.save(uom4);

		
	}
}