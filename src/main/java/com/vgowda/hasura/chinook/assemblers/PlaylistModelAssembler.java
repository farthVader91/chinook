package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.PlaylistController;
import com.vgowda.hasura.chinook.models.Playlist;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PlaylistModelAssembler implements RepresentationModelAssembler<
    Playlist, EntityModel<Playlist>> {
  @Override
  public EntityModel<Playlist> toModel(Playlist entity) {
    return EntityModel.of(entity,
        linkTo(methodOn(PlaylistController.class)
            .getPlaylistById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(PlaylistController.class)
            .getTracksByPlaylist(entity.getId(), PageRequest.of(0, 20)))
            .withRel("tracks"));
  }
}
