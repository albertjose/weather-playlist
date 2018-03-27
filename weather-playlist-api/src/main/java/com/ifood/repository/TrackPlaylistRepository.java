package com.ifood.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.cache.TrackPlaylistCache;

@Repository
public interface TrackPlaylistRepository extends CrudRepository<TrackPlaylistCache, String> {
	List<TrackPlaylistCache> findByPlaylistId(String playlistId);
}
