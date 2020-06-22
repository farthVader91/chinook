package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.FilmActor;
import org.springframework.data.repository.CrudRepository;

public interface FilmActorRepository extends CrudRepository<FilmActor, Long> {
}
