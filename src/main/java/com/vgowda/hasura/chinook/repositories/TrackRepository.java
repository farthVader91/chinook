package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Track;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrackRepository extends PagingAndSortingRepository<Track, Long> {
  List<Track> findAllByAlbumId(Long albumId);

  @Query(
      value = "SELECT tr.* from tracks tr join playlist_track pt " +
          "on tr.id = pt.track_id where pt.playlist_id = ?1",
      nativeQuery = true)
  Page<Track> findAllByPlaylistId(Long playlistId, Pageable pageable);
}
