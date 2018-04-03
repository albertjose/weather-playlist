package com.ifood.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifood.client.fallback.SpotifyClientFallbackFactory;
import com.ifood.config.CoreFeignConfiguration;
import com.ifood.domain.ResultPlaylistCategory;
import com.ifood.domain.ResultTrackPlaylist;

@FeignClient(url = "${app.spotify.api-url}", name = "spotify-client", fallbackFactory = SpotifyClientFallbackFactory.class, configuration = CoreFeignConfiguration.class)
public interface SpotifyClient {

	@GetMapping(value = "/browse/categories/{categoryId}/playlists", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResultPlaylistCategory getPlaylistByCategory(@RequestHeader("Authorization") String authorization,
			@PathVariable("categoryId") String categoryId);

	@GetMapping(value = "/users/spotify/playlists/{playlistId}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResultTrackPlaylist getTracksByPlaylistId(@RequestHeader("Authorization") String authorization,
			@PathVariable("playlistId") String playlistId);
}
