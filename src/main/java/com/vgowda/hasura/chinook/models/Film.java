package com.vgowda.hasura.chinook.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name = "films")
@ToString(exclude = {"category"})
@EqualsAndHashCode(exclude = "category")
public class Film {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  private String title;
  private String description;
  private long releaseYear;
  private long languageId;
  private long rentalDuration;
  private String rentalRate;
  private long length;
  private String replacementCost;
  private String rating;
  private LocalDateTime lastUpdate;
  private String specialFeatures;
  private String fulltext;
  @JoinTable(
      name = "film_category",
      joinColumns = @JoinColumn(
          name = "film_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "category_id",
          referencedColumnName = "id"))
  @ManyToOne
  private Category category;

}
