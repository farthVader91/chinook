package com.vgowda.hasura.chinook.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "film_actor")
@IdClass(FilmActor.FilmActorId.class)
public class FilmActor {
  @Id
  private long actorId;
  @Id
  private long filmId;
  private LocalDateTime lastUpdate;

  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  static class FilmActorId implements Serializable {
    private long actorId;
    private long filmId;
  }
}
