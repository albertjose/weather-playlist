package com.ifood.service.client;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ifood.cache.SpotifyTokenCacheDecorator;
import com.ifood.client.SpotifyAuthClient;
import com.ifood.domain.SpotifyToken;
import com.ifood.exception.SpotifyAuthException;

@Service
public class SpotifyAuthService {

	@Value("${app.spotify.auth.client-id}")
	private String clientId;

	@Value("${app.spotify.auth.client-secret}")
	private String clientSecret;

	SpotifyAuthClient spotifyAuthClient;

	SpotifyTokenCacheDecorator spotifyTokenCache;

	@Autowired
	public SpotifyAuthService(SpotifyAuthClient spotifyAuthClient, SpotifyTokenCacheDecorator spotifyTokenCache) {
		this.spotifyAuthClient = spotifyAuthClient;
		this.spotifyTokenCache = spotifyTokenCache;
	}

	public SpotifyToken getToken() throws SpotifyAuthException {
		SpotifyToken spotifyToken = spotifyTokenCache.findOne();
		if (spotifyToken == null) {
			spotifyToken = generateToken();
		}

		if (spotifyToken == null) {
			throw new SpotifyAuthException("Cannot authenticate in spotify.");
		}

		spotifyTokenCache.save(spotifyToken);
		return spotifyToken;
	}

	private SpotifyToken generateToken() {
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credentials");
		String clientCredentials = String.format("%s:%s", clientId, clientSecret);
		String credentialsHeader = "Basic " + Base64.getEncoder().encodeToString(clientCredentials.getBytes());

		return spotifyAuthClient.generateTokenAcess(credentialsHeader, params);
	}

}
