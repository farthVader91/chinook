package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, Long> {
  @Query(
      value = "SELECT pl.* from playlists pl join playlist_track pt " +
          "on pl.id = pt.playlist_id where pt.track_id = ?1",
      nativeQuery = true)
  Page<Playlist> findAllByTrackId(Long trackId, Pageable pageable);
}
