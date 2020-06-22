package com.vgowda.hasura.chinook.repositories;

import com.vgowda.hasura.chinook.models.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArtistRepository extends PagingAndSortingRepository<Artist, Long> {
}
