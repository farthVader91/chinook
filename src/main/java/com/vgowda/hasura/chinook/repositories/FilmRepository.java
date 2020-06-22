package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Category;
import com.vgowda.hasura.chinook.models.Film;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Long> {
  List<Film> getAllByCategory(Category category);
}
