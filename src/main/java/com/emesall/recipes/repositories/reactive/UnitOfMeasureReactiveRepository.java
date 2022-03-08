package com.emesall.recipes.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.emesall.recipes.model.UnitOfMeasure;

import reactor.core.publisher.Mono;

@Repository
public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

	Mono<UnitOfMeasure> findByDescription(String name);
}
