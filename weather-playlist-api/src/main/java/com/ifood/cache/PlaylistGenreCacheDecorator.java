package com.ifood.cache;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.PlaylistGenreCache;
import com.ifood.repository.PlaylistGenreRepository;

@Service
public class PlaylistGenreCacheDecorator extends CacheDecorator<PlaylistGenreCache> {

	protected PlaylistGenreCacheDecorator(CrudRepository<PlaylistGenreCache, String> repository) {
		super(repository);
	}

	public PlaylistGenreCache findOneByGenre(String keyid) {
		return ((PlaylistGenreRepository) repository).findOneByGenre(keyid);
	}

	public void saveAll(List<PlaylistGenreCache> entities) {
		repository.saveAll(entities);
	}

}
