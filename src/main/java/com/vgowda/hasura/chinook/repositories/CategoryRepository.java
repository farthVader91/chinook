package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
