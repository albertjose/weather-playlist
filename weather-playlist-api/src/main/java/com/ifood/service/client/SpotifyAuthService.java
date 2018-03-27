package com.ifood.service.client;

import com.ifood.domain.SpotifyToken;
import com.ifood.exception.SpotifyAuthException;

public interface SpotifyAuthService {
	SpotifyToken getToken() throws SpotifyAuthException;
}
