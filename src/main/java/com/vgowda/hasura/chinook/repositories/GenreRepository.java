package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
