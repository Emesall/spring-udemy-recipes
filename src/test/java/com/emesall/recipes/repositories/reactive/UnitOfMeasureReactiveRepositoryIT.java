package com.emesall.recipes.repositories.reactive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.emesall.recipes.model.UnitOfMeasure;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureReactiveRepositoryIT {

	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
		unitOfMeasureRepository.deleteAll().block();
	}

	@Test
	void testSaveUom() {
		UnitOfMeasure uom=new UnitOfMeasure();
		uom.setDescription("test");
		unitOfMeasureRepository.save(uom).block();
		
		assertEquals(1L, unitOfMeasureRepository.count().block());
		
	}

	@Test
	void testFindByDescription() {
		UnitOfMeasure uom=new UnitOfMeasure();
		uom.setDescription("test");
		unitOfMeasureRepository.save(uom).block();
		UnitOfMeasure unitOfMeasure= unitOfMeasureRepository.findByDescription("test").block();
		
		assertEquals("test", unitOfMeasure.getDescription());
	}

}
