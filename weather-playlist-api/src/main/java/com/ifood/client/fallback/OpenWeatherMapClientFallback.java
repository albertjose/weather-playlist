package com.ifood.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifood.client.OpenWeatherMapClient;
import com.ifood.domain.ResultCityWeather;

public class OpenWeatherMapClientFallback implements OpenWeatherMapClient {
	private static final Logger logger = LoggerFactory.getLogger(OpenWeatherMapClientFallback.class);

	private final Throwable cause;

	public OpenWeatherMapClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResultCityWeather getWeatherByCityName(String city_name) {
		logger.error(String.format("Fallback to getWeatherByCityName(%s)", city_name), cause);
		return null;
	}

	@Override
	public ResultCityWeather getWeatherByLatLong(Double lat, Double lon) {
		logger.error(String.format("Fallback to getWeatherByLatLong(%f, %f)", lat, lon), cause);
		return null;
	}

	@Override
	public ResultCityWeather getWeatherById(Long id) {
		logger.error(String.format("Fallback to getWeatherByLatLong(%d)", id), cause);
		return null;
	}
}
