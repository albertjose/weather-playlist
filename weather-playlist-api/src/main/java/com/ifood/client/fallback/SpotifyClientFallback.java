package com.ifood.client.fallback;

import org.springframework.stereotype.Component;

import com.ifood.client.SpotifyClient;
import com.ifood.domain.ResultPlaylistCategory;
import com.ifood.domain.ResultTrackPlaylist;

@Component
public class SpotifyClientFallback implements SpotifyClient {

	@Override
	public ResultPlaylistCategory getPlaylistByCategory(String authorization, String categoryId) {
		return null;
	}

	@Override
	public ResultTrackPlaylist getTracksByPlaylistId(String authorization, String playlistId) {
		return null;
	}

}
