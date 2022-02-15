package com.emesall.recipes.services;

import java.util.Set;

import com.emesall.recipes.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listUoM();
}
