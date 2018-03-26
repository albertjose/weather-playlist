package com.ifood.domain;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;

@RedisHash("spotifyToken")
public class SpotifyToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private Long expiresIn;

	public SpotifyToken() {
		super();
	}

	public SpotifyToken(String accessToken, String tokenType, Long expiresIn) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String access_token) {
		this.accessToken = access_token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String token_type) {
		this.tokenType = token_type;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expires_in) {
		this.expiresIn = expires_in;
	}

}