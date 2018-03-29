package com.ifood.client.fallback;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifood.client.SpotifyAuthClient;
import com.ifood.domain.SpotifyToken;

public class SpotifyAuthClientFallback implements SpotifyAuthClient {

	private static final Logger logger = LoggerFactory.getLogger(SpotifyAuthClientFallback.class);

	private final Throwable cause;

	public SpotifyAuthClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public SpotifyToken generateTokenAccess(String credentialsHeader, Map<String, String> params) {
		logger.error("Fallback to generateTokenAccess(credentialsHeader, params)", cause);
		return null;
	}

}
