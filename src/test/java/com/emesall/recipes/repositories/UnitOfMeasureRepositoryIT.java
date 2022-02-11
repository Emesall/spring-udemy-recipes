package com.emesall.recipes.repositories;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.emesall.recipes.model.UnitOfMeasure;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {
	
	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void testFindByDescription() {
		Optional<UnitOfMeasure> unitOfMeasure=unitOfMeasureRepository.findByDescription("pinch");
		assertEquals("pinch", unitOfMeasure.get().getDescription());
	}

}
