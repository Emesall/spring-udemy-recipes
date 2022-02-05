package com.emesall.recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emesall.recipes.model.Category;
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
