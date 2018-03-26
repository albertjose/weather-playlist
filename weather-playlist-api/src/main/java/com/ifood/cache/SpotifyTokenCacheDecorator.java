package com.ifood.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.SpotifyToken;

@Service
public class SpotifyTokenCacheDecorator extends CacheDecorator<SpotifyToken> {

	protected SpotifyTokenCacheDecorator(CrudRepository<SpotifyToken, String> repository) {
		super(repository);
	}

	public SpotifyToken findOne() {
		Iterable<SpotifyToken> spotifyToken = repository.findAll();
		if (spotifyToken.iterator().hasNext()) {
			return spotifyToken.iterator().next();
		}
		return null;
	}

}
