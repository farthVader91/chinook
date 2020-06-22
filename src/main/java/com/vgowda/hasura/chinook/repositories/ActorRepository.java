package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
