package com.vgowda.hasura.chinook.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.vgowda.hasura.chinook.assemblers.AlbumModelAssembler;
import com.vgowda.hasura.chinook.assemblers.TrackModelAssembler;
import com.vgowda.hasura.chinook.models.Album;
import com.vgowda.hasura.chinook.models.Track;
import com.vgowda.hasura.chinook.repositories.AlbumRepository;
import com.vgowda.hasura.chinook.repositories.TrackRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
  private final AlbumRepository albumRepository;
  private final TrackRepository trackRepository;
  private final AlbumModelAssembler albumModelAssembler;
  private final TrackModelAssembler trackModelAssembler;
  private final PagedResourcesAssembler<Album> pagedAlbumAssembler;

  @Autowired
  public AlbumController(
      AlbumRepository albumRepository,
      TrackRepository trackRepository, AlbumModelAssembler albumModelAssembler,
      TrackModelAssembler trackModelAssembler, PagedResourcesAssembler<Album> pagedAlbumAssembler) {
    this.albumRepository = albumRepository;
    this.trackRepository = trackRepository;
    this.albumModelAssembler = albumModelAssembler;
    this.trackModelAssembler = trackModelAssembler;
    this.pagedAlbumAssembler = pagedAlbumAssembler;
  }

  @GetMapping("/{albumId}")
  public EntityModel<Album> getAlbumById(@PathVariable Long albumId) {
    Album album = albumRepository.findById(albumId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "album does not exist"));

    return albumModelAssembler.toModel(album);
  }

  @GetMapping
  public CollectionModel<EntityModel<Album>> getAllAlbums(Pageable pageable) {
    Page<Album> albums = albumRepository.findAll(pageable);

    return pagedAlbumAssembler.toModel(albums, albumModelAssembler);
  }

  @GetMapping("/{albumId}/tracks")
  public CollectionModel<EntityModel<Track>> getTracksByAlbum(@PathVariable Long albumId) {
    List<Track> tracks = trackRepository.findAllByAlbumId(albumId);

    return trackModelAssembler.toCollectionModel(tracks)
        .add(linkTo(methodOn(AlbumController.class)
            .getTracksByAlbum(albumId))
            .withSelfRel());
  }
}
