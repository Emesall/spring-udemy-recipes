package com.emesall.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.recipes.model.UnitOfMeasure;
@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

}
