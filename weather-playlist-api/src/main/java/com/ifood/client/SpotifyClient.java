package com.ifood.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ifood.client.fallback.SpotifyClientFallback;
import com.ifood.domain.ResultPlaylistCategory;
import com.ifood.domain.ResultTrackPlaylist;

@FeignClient(url = "${app.spotify.api-url}", name = "spotify-client", fallback = SpotifyClientFallback.class)
public interface SpotifyClient {

	@GetMapping(value = "/browse/categories/{categoryId}/playlists")
	ResultPlaylistCategory getPlaylistByCategory(@RequestHeader("Authorization") String authorization,
			@PathVariable("categoryId") String categoryId);

	@GetMapping(value = "/users/spotify/playlists/{playlistId}/tracks")
	ResultTrackPlaylist getTracksByPlaylistId(@RequestHeader("Authorization") String authorization,
			@PathVariable("playlistId") String playlistId);
}
