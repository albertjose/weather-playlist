package com.ifood.builder;

import com.ifood.domain.cache.TrackPlaylistCache;

public class TrackPlaylistCacheBuilder {

	private String trackName = ResultTrackItemBuilder.build().now().getName();
	private String playlistId = ResultPlaylistItemBuilder.build().now().getId();

	public static TrackPlaylistCacheBuilder build() {
		return new TrackPlaylistCacheBuilder();
	}

	public TrackPlaylistCache now() {
		return new TrackPlaylistCache(trackName, playlistId);
	}

}
