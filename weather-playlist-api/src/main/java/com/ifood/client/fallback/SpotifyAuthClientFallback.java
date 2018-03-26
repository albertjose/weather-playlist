package com.ifood.client.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ifood.client.SpotifyAuthClient;
import com.ifood.domain.SpotifyToken;

@Component
public class SpotifyAuthClientFallback implements SpotifyAuthClient {

	@Override
	public SpotifyToken generateTokenAccess(String credentialsHeader, Map<String, String> params) {
		return null;
	}

}
