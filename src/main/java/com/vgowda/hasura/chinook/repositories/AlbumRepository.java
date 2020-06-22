package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlbumRepository extends PagingAndSortingRepository<Album, Long> {
  Page<Album> getAllByArtistId(Long artistId, Pageable pageable);
}
