package com.ifood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.SpotifyToken;

@Repository
public interface SpotifyTokenRepository extends CrudRepository<SpotifyToken, String> {
}
