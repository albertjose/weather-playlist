package com.ifood.client.fallback;

import org.springframework.stereotype.Component;

import com.ifood.client.SpotifyAuthClient;

import feign.hystrix.FallbackFactory;

@Component
public class SpotifyAuthClientFallbackFactory implements FallbackFactory<SpotifyAuthClient> {

	@Override
	public SpotifyAuthClientFallback create(Throwable throwable) {
		return new SpotifyAuthClientFallback(throwable);
	}

}
