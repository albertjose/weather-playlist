package com.ifood.repository.spotify;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.cache.SpotifyTokenCache;

@Repository
public interface SpotifyTokenRepository extends CrudRepository<SpotifyTokenCache, String> {
}
