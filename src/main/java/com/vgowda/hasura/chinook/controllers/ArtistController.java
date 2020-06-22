package com.vgowda.hasura.chinook.controllers;

import com.vgowda.hasura.chinook.assemblers.AlbumModelAssembler;
import com.vgowda.hasura.chinook.assemblers.ArtistModelAssembler;
import com.vgowda.hasura.chinook.models.Album;
import com.vgowda.hasura.chinook.models.Artist;
import com.vgowda.hasura.chinook.repositories.AlbumRepository;
import com.vgowda.hasura.chinook.repositories.ArtistRepository;
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
@RequestMapping("/api/artists")
public class ArtistController {
  private final ArtistRepository artistRepository;
  private final AlbumRepository albumRepository;
  private final ArtistModelAssembler assembler;
  private final AlbumModelAssembler albumModelAssembler;
  private final PagedResourcesAssembler<Artist> pagedArtistAssembler;
  private final PagedResourcesAssembler<Album> pagedAlbumAssembler;

  @Autowired
  public ArtistController(
      ArtistRepository artistRepository,
      AlbumRepository albumRepository,
      ArtistModelAssembler assembler,
      PagedResourcesAssembler<Artist> pagedArtistAssembler,
      AlbumModelAssembler albumModelAssembler,
      PagedResourcesAssembler<Album> pagedAlbumAssembler) {
    this.artistRepository = artistRepository;
    this.albumRepository = albumRepository;
    this.assembler = assembler;
    this.pagedAlbumAssembler = pagedAlbumAssembler;
    this.albumModelAssembler = albumModelAssembler;
    this.pagedArtistAssembler = pagedArtistAssembler;
  }

  @GetMapping("/{artistId}")
  public EntityModel<Artist> getArtistById(@PathVariable Long artistId) {
    Artist artist = artistRepository.findById(artistId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "artist does not exist"));

    return assembler.toModel(artist);
  }

  @GetMapping
  public CollectionModel<EntityModel<Artist>> getAllArtists(Pageable pageable) {
    Page<Artist> artists = artistRepository.findAll(pageable);

    return pagedArtistAssembler.toModel(artists, assembler);
  }

  @GetMapping("/{artistId}/albums")
  public CollectionModel<EntityModel<Album>> getAlbumsByArtist(@PathVariable Long artistId, Pageable pageable) {
    Page<Album> albums = albumRepository.getAllByArtistId(artistId, pageable);

    return pagedAlbumAssembler.toModel(albums, albumModelAssembler);
  }
}
