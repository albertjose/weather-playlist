package com.ifood.client.fallback;

import org.springframework.stereotype.Component;

import com.ifood.client.OpenWeatherMapClient;
import com.ifood.domain.ResultCityWeather;

@Component
public class OpenWeatherMapClientFallback implements OpenWeatherMapClient {

	@Override
	public ResultCityWeather getWeatherByCityName(String city_name) {
		return null;
	}

	@Override
	public ResultCityWeather getWeatherByLatLong(Double lat, Double lon) {
		return null;
	}

	@Override
	public ResultCityWeather getWeatherById(Long id) {
		return null;
	}
}
