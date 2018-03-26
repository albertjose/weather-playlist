package com.ifood.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.SpotifyTokenCache;

@Service
public class SpotifyTokenCacheDecorator extends CacheDecorator<SpotifyTokenCache> {

	protected SpotifyTokenCacheDecorator(CrudRepository<SpotifyTokenCache, String> repository) {
		super(repository);
	}

	public SpotifyTokenCache findOne() {
		Iterable<SpotifyTokenCache> spotifyToken = repository.findAll();
		if (spotifyToken.iterator().hasNext()) {
			return spotifyToken.iterator().next();
		}
		return null;
	}

}
