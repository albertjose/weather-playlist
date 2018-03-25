package com.ifood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.client.OpenWeatherMapClient;
import com.ifood.domain.CityWeatherResponse;

@Service
public class WeatherService {

	@Autowired
	OpenWeatherMapClient weatherClient;

	@Autowired
	CacheService cacheService;

	public Double searchByCityName(String cityName) {
		Double temperature = null;
		// search id in redis
		String cityId = cacheService.findCachedId(cityName);
		if (cityId != null) {
			// search temp in redis
			temperature = cacheService.findCachedTemperature(cityId);
		}

		if (temperature == null) {
			// not found in redis -> search in api
			CityWeatherResponse response = null;
			try {
				response = weatherClient.getWeatherByCityMap(cityName);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (response != null) {
				cacheService.saveCityTemperatureCache(response);
				temperature = response.getTemperature();
			}
		}
		return temperature;
	}

	public Double searchWeatherByLatLong(Double lat, Double lon) {
		Double temperature = null;
		// search id in redis
		String cityId = cacheService.findCachedId(lat, lon);
		if (cityId != null) {
			// search temp in redis
			temperature = cacheService.findCachedTemperature(cityId);
		}

		if (temperature == null) {
			// not found in redis -> search in api
			CityWeatherResponse response = weatherClient.getWeatherByLatLong(lat, lon);

			if (response != null) {
				cacheService.saveCityTemperatureCache(response);
				temperature = response.getTemperature();
			}
		}
		return temperature;
	}

}
