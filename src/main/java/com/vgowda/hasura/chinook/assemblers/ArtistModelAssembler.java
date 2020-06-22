package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.ArtistController;
import com.vgowda.hasura.chinook.models.Artist;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ArtistModelAssembler implements RepresentationModelAssembler<
    Artist, EntityModel<Artist>> {
  @Override
  public EntityModel<Artist> toModel(Artist entity) {
    return EntityModel.of(entity,
        linkTo(methodOn(ArtistController.class)
            .getArtistById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(ArtistController.class)
            .getAlbumsByArtist(entity.getId(), PageRequest.of(0, 20)))
            .withRel("albums"));
  }
}
