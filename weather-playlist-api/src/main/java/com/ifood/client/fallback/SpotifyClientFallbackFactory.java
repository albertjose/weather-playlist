package com.ifood.client.fallback;

import org.springframework.stereotype.Component;

import com.ifood.client.SpotifyClient;

import feign.hystrix.FallbackFactory;

@Component
public class SpotifyClientFallbackFactory implements FallbackFactory<SpotifyClient> {

	@Override
	public SpotifyClientFallback create(Throwable throwable) {
		return new SpotifyClientFallback(throwable);
	}

}
