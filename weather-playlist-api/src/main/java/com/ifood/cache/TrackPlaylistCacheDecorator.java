package com.ifood.cache;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.TrackPlaylistCache;
import com.ifood.repository.spotify.TrackPlaylistRepository;

@Service
public class TrackPlaylistCacheDecorator extends CacheDecorator<TrackPlaylistCache> {

	protected TrackPlaylistCacheDecorator(CrudRepository<TrackPlaylistCache, String> repository) {
		super(repository);
	}

	public void saveAll(List<TrackPlaylistCache> entities) {
		repository.saveAll(entities);
	}

	public List<TrackPlaylistCache> findByPlaylistId(String playlistId) {
		List<TrackPlaylistCache> trackList = ((TrackPlaylistRepository) repository).findByPlaylistId(playlistId);
		if (trackList != null && !trackList.isEmpty()) {
			return trackList;
		}
		return null;
	}

}
