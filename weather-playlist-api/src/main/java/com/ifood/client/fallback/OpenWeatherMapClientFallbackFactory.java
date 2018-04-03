package com.ifood.client.fallback;

import org.springframework.stereotype.Component;

import com.ifood.client.OpenWeatherMapClient;

import feign.hystrix.FallbackFactory;

@Component
public class OpenWeatherMapClientFallbackFactory implements FallbackFactory<OpenWeatherMapClient> {

	@Override
	public OpenWeatherMapClientFallback create(Throwable throwable) {
		return new OpenWeatherMapClientFallback(throwable);
	}

}
