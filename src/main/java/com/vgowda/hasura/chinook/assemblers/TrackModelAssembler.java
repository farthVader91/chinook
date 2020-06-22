package com.vgowda.hasura.chinook.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.controllers.AlbumController;
import com.vgowda.hasura.chinook.controllers.TrackController;
import com.vgowda.hasura.chinook.models.Track;
import com.vgowda.hasura.chinook.repositories.InvoiceLineRepository;
import com.vgowda.hasura.chinook.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TrackModelAssembler implements RepresentationModelAssembler<Track, EntityModel<Track>> {
  private final InvoiceLineRepository invoiceLineRepository;
  private final PlaylistRepository playlistRepository;

  @Autowired
  public TrackModelAssembler(InvoiceLineRepository invoiceLineRepository,
                             PlaylistRepository playlistRepository) {
    this.invoiceLineRepository = invoiceLineRepository;
    this.playlistRepository = playlistRepository;
  }

  @Override
  public EntityModel<Track> toModel(Track entity) {
    EntityModel<Track> track = EntityModel.of(entity,
        linkTo(methodOn(TrackController.class)
            .getTrackById(entity.getId()))
            .withSelfRel(),
        linkTo(methodOn(AlbumController.class)
            .getAlbumById(entity.getAlbumId()))
            .withRel("album"));
    PageRequest pageRequest = PageRequest.of(0, 20);
    if (invoiceLineRepository
        .findAllByTrackId(entity.getId(), pageRequest)
        .getTotalElements() > 0) {
      track.add(linkTo(methodOn(TrackController.class)
          .getAllInvoiceLinesByTrackId(entity.getId(), PageRequest.of(0, 20)))
          .withRel("invoice-lines"));
    }
    if (playlistRepository
        .findAllByTrackId(entity.getId(), pageRequest)
        .getTotalElements() > 0) {
      track.add(linkTo(methodOn(TrackController.class)
          .getAllPlaylistssByTrackId(entity.getId(), PageRequest.of(0, 20)))
          .withRel("playlists"));
    }

    return track;
  }
}
