package com.ifood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.cache.PlaylistGenreCache;

@Repository
public interface PlaylistGenreRepository extends CrudRepository<PlaylistGenreCache, String> {
	PlaylistGenreCache findOneByGenre(String genre);
}
