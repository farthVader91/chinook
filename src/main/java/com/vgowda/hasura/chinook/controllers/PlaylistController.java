package com.vgowda.hasura.chinook.controllers;

import com.vgowda.hasura.chinook.assemblers.PlaylistModelAssembler;
import com.vgowda.hasura.chinook.assemblers.TrackModelAssembler;
import com.vgowda.hasura.chinook.models.Playlist;
import com.vgowda.hasura.chinook.models.Track;
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
@RequestMapping("/api/playlists")
public class PlaylistController {
  private final PlaylistRepository playlistRepository;
  private final TrackRepository trackRepository;
  private final PlaylistModelAssembler playlistModelAssembler;
  private final TrackModelAssembler trackModelAssembler;
  private final PagedResourcesAssembler<Playlist> playlistPagedResourcesAssembler;
  private final PagedResourcesAssembler<Track> trackPagedResourcesAssembler;

  @Autowired
  public PlaylistController(
      PlaylistRepository playlistRepository,
      TrackRepository trackRepository, PlaylistModelAssembler playlistModelAssembler,
      TrackModelAssembler trackModelAssembler,
      PagedResourcesAssembler<Playlist> playlistPagedResourcesAssembler,
      PagedResourcesAssembler<Track> trackPagedResourcesAssembler) {
    this.playlistRepository = playlistRepository;
    this.trackRepository = trackRepository;
    this.playlistModelAssembler = playlistModelAssembler;
    this.trackModelAssembler = trackModelAssembler;
    this.playlistPagedResourcesAssembler = playlistPagedResourcesAssembler;
    this.trackPagedResourcesAssembler = trackPagedResourcesAssembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Playlist>> getAllPlaylists(Pageable pageable) {
    Page<Playlist> page = playlistRepository.findAll(pageable);

    return playlistPagedResourcesAssembler.toModel(page, playlistModelAssembler);
  }

  @GetMapping("/{playlistId}")
  public EntityModel<Playlist> getPlaylistById(@PathVariable Long playlistId) {
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "playlist does not exist"));

    return playlistModelAssembler.toModel(playlist);
  }

  @GetMapping("/{playlistId}/tracks")
  public CollectionModel<EntityModel<Track>> getTracksByPlaylist(
      @PathVariable Long playlistId, Pageable pageable) {
    Page<Track> page = trackRepository.findAllByPlaylistId(playlistId, pageable);

    return trackPagedResourcesAssembler.toModel(page, trackModelAssembler);
  }
}
