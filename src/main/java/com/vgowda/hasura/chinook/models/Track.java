package com.vgowda.hasura.chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Entity(name = "tracks")
public class Track extends RepresentationModel<Track> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  private String name;
  @JsonIgnore
  private Long albumId;
  @OneToOne
  @JoinColumn(name = "media_type_id", referencedColumnName = "id")
  private MediaType mediaType;
  @OneToOne
  @JoinColumn(name = "genre_id", referencedColumnName = "id")
  private Genre genre;
  private String composer;
  private long milliseconds;
  private long bytes;
  private String unitPrice;
}
