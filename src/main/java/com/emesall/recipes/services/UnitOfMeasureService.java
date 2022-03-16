package com.emesall.recipes.services;

import com.emesall.recipes.commands.UnitOfMeasureCommand;
import com.emesall.recipes.model.UnitOfMeasure;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listUoM();
	Mono<UnitOfMeasure> findById(String id);
}
