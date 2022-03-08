package com.emesall.recipes.services;

import com.emesall.recipes.commands.UnitOfMeasureCommand;

import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listUoM();
}
