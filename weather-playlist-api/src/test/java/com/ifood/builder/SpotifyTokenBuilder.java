package com.ifood.builder;

import com.ifood.domain.SpotifyToken;

public class SpotifyTokenBuilder {

	private static final long EXPIRES_TIME_IN = 3600L;
	private static final String TOKEN_TYPE = "bearer";
	private static final String ACCESS_TOKEN = "i23j21i3ji21";

	private String accessToken = ACCESS_TOKEN;
	private String tokenType = TOKEN_TYPE;
	private Long expiresIn = EXPIRES_TIME_IN;

	public static SpotifyTokenBuilder build() {
		return new SpotifyTokenBuilder();
	}

	public SpotifyToken now() {
		return new SpotifyToken(accessToken, tokenType, expiresIn);
	}

}
