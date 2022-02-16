package com.emesall.recipes.services;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emesall.recipes.commands.UnitOfMeasureCommand;
import com.emesall.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.emesall.recipes.repositories.UnitOfMeasureRepository;

@Service
public class UnifOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand converter;

	@Autowired
	public UnifOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand converter) {
		super();
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.converter = converter;
	}

	public Set<UnitOfMeasureCommand> listUoM() {

		
		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false).map(converter::convert)
				.collect(Collectors.toSet());

	}
}
