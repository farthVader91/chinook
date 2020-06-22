package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.AlbumController;
import com.vgowda.hasura.chinook.controllers.ArtistController;
import com.vgowda.hasura.chinook.models.Album;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AlbumModelAssembler implements RepresentationModelAssembler<Album, EntityModel<Album>> {
  @Override
  public EntityModel<Album> toModel(Album entity) {
    return EntityModel.of(entity,
        linkTo(methodOn(AlbumController.class)
            .getAlbumById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(ArtistController.class)
            .getArtistById(entity.getArtistId()))
            .withRel("artist"),
        linkTo(methodOn(AlbumController.class)
            .getTracksByAlbum(entity.getId()))
            .withRel("tracks")
    );
  }
}
