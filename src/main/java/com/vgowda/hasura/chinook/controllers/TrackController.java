package com.vgowda.hasura.chinook.controllers;

import com.vgowda.hasura.chinook.assemblers.InvoiceLineModelAssembler;
import com.vgowda.hasura.chinook.assemblers.PlaylistModelAssembler;
import com.vgowda.hasura.chinook.assemblers.TrackModelAssembler;
import com.vgowda.hasura.chinook.models.InvoiceLine;
import com.vgowda.hasura.chinook.models.Playlist;
import com.vgowda.hasura.chinook.models.Track;
import com.vgowda.hasura.chinook.repositories.InvoiceLineRepository;
import com.vgowda.hasura.chinook.repositories.PlaylistRepository;
import com.vgowda.hasura.chinook.repositories.TrackRepository;
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
@RequestMapping("/api/tracks")
public class TrackController {
  private final TrackRepository trackRepository;
  private final TrackModelAssembler trackModelAssembler;
  private final PlaylistRepository playlistRepository;
  private final PagedResourcesAssembler<Track> pagedResourcesAssembler;
  private final PagedResourcesAssembler<InvoiceLine> invoiceLinePagedResourcesAssembler;
  private final PagedResourcesAssembler<Playlist> playlistPagedResourcesAssembler;
  private final InvoiceLineRepository invoiceLineRepository;
  private final InvoiceLineModelAssembler invoiceLineModelAssembler;
  private final PlaylistModelAssembler playlistModelAssembler;

  @Autowired
  public TrackController(
      TrackRepository trackRepository,
      TrackModelAssembler trackModelAssembler,
      PlaylistRepository playlistRepository,
      PagedResourcesAssembler<Track> pagedResourcesAssembler,
      PagedResourcesAssembler<InvoiceLine> invoiceLinePagedResourcesAssembler,
      PagedResourcesAssembler<Playlist> playlistPagedResourcesAssembler,
      InvoiceLineRepository invoiceLineRepository,
      InvoiceLineModelAssembler invoiceLineModelAssembler,
      PlaylistModelAssembler playlistModelAssembler) {
    this.trackRepository = trackRepository;
    this.trackModelAssembler = trackModelAssembler;
    this.playlistRepository = playlistRepository;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
    this.invoiceLinePagedResourcesAssembler = invoiceLinePagedResourcesAssembler;
    this.playlistPagedResourcesAssembler = playlistPagedResourcesAssembler;
    this.invoiceLineRepository = invoiceLineRepository;
    this.invoiceLineModelAssembler = invoiceLineModelAssembler;
    this.playlistModelAssembler = playlistModelAssembler;
  }

  @GetMapping("/{trackId}")
  public EntityModel<Track> getTrackById(@PathVariable Long trackId) {
    Track track = trackRepository.findById(trackId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "track does not exist"));

    return trackModelAssembler.toModel(track);
  }

  @GetMapping
  public CollectionModel<EntityModel<Track>> getAllTracks(Pageable pageable) {
    Page<Track> tracks = trackRepository.findAll(pageable);

    return pagedResourcesAssembler.toModel(tracks, trackModelAssembler);
  }

  @GetMapping("/{trackId}/invoice-lines")
  public CollectionModel<EntityModel<InvoiceLine>> getAllInvoiceLinesByTrackId(
      @PathVariable Long trackId, Pageable pageable) {
    Page<InvoiceLine> page = invoiceLineRepository.findAllByTrackId(trackId, pageable);

    return invoiceLinePagedResourcesAssembler.toModel(page, invoiceLineModelAssembler);
  }

  @GetMapping("/{trackId}/playlists")
  public CollectionModel<EntityModel<Playlist>> getAllPlaylistssByTrackId(
      @PathVariable Long trackId, Pageable pageable) {
    Page<Playlist> page = playlistRepository.findAllByTrackId(trackId, pageable);

    return playlistPagedResourcesAssembler.toModel(page, playlistModelAssembler);
  }
}
