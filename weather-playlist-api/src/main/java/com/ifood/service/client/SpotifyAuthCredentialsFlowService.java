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
import com.ifood.domain.cache.SpotifyTokenCache;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.helper.MapperHelper;

@Service
public class SpotifyAuthCredentialsFlowService implements SpotifyAuthService {

	@Value("${app.spotify.auth.client-id}")
	private String clientId;

	@Value("${app.spotify.auth.client-secret}")
	private String clientSecret;

	SpotifyAuthClient spotifyAuthClient;

	SpotifyTokenCacheDecorator spotifyTokenCache;

	MapperHelper mapperHelper;

	@Autowired
	public SpotifyAuthCredentialsFlowService(SpotifyAuthClient spotifyAuthClient,
			SpotifyTokenCacheDecorator spotifyTokenCache, MapperHelper mapperHelper) {
		this.spotifyAuthClient = spotifyAuthClient;
		this.spotifyTokenCache = spotifyTokenCache;
		this.mapperHelper = mapperHelper;
	}

	/**
	 * Get token from cache if exist, else generate new
	 * 
	 * @return
	 * @throws SpotifyAuthException
	 */
	@Override
	public SpotifyToken getToken() throws SpotifyAuthException {
		SpotifyToken spotifyToken = null;

		SpotifyTokenCache tokenCached = spotifyTokenCache.findOne();

		if (tokenCached != null) {
			return mapperHelper.fromObject(tokenCached, SpotifyToken.class);
		} else {
			spotifyToken = generateToken();

			if (spotifyToken == null) {
				throw new SpotifyAuthException("Sorry. Failed to authenticate on spotify partner.");
			}
			spotifyTokenCache.save(mapperHelper.fromObject(spotifyToken, SpotifyTokenCache.class));

			return spotifyToken;
		}
	}

	/**
	 * Generate a new token
	 * 
	 * @return
	 */
	private SpotifyToken generateToken() {
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "client_credentials");
		String credentialsHeader = "Basic "
				+ Base64.getEncoder().encodeToString(String.format("%s:%s", clientId, clientSecret).getBytes());

		return spotifyAuthClient.generateTokenAccess(credentialsHeader, params);
	}
}
