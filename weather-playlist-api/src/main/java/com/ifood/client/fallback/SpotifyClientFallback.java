package com.ifood.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifood.client.SpotifyClient;
import com.ifood.domain.ResultPlaylistCategory;
import com.ifood.domain.ResultTrackPlaylist;

public class SpotifyClientFallback implements SpotifyClient {
	private static final Logger logger = LoggerFactory.getLogger(SpotifyClientFallback.class);

	private final Throwable cause;

	public SpotifyClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResultPlaylistCategory getPlaylistByCategory(String authorization, String categoryId) {
		logger.error(String.format("Fallback to getPlaylistByCategory(authorization, %s)", categoryId), cause);
		return null;
	}

	@Override
	public ResultTrackPlaylist getTracksByPlaylistId(String authorization, String playlistId) {
		logger.error(String.format("Fallback to getTracksByPlaylistId(authorization, %s)", playlistId), cause);
		return null;
	}

}
