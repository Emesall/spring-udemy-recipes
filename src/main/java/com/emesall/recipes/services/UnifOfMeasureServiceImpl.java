package com.emesall.recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.recipes.commands.UnitOfMeasureCommand;
import com.emesall.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.emesall.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;

import reactor.core.publisher.Flux;

@Service
public class UnifOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand converter;

	@Autowired
	public UnifOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand converter) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.converter = converter;
	}

	public Flux<UnitOfMeasureCommand> listUoM() {

		
		return unitOfMeasureRepository.findAll().map(converter::convert);

	}
}
