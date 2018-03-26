package com.ifood.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifood.domain.SpotifyToken;

@FeignClient(url = "${app.spotify.auth.url}", name = "spotify-auth")
public interface SpotifyAuthClient {

	@PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	SpotifyToken generateTokenAcess(@RequestHeader("Authorization") String credentialsHeader,
			@RequestParam Map<String, String> params);
}
