package com.ifood.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.cache.CityCoordinateDecorator;
import com.ifood.cache.CityNameCacheDecorator;
import com.ifood.cache.TemperatureCacheService;
import com.ifood.client.OpenWeatherMapClient;
import com.ifood.domain.ResultCityWeather;
import com.ifood.domain.cache.CityCoordinateCache;
import com.ifood.domain.cache.CityNameCache;
import com.ifood.domain.cache.TemperatureCache;
import com.ifood.exception.OpenWeatherMapResultException;

@Service
public class WeatherService {

	private static final String CITY_COORDINATE_KEY_PATTERN = "%f:%f";

	OpenWeatherMapClient weatherClient;

	CityNameCacheDecorator cityNameCache;

	TemperatureCacheService temperatureCache;

	CityCoordinateDecorator cityCoordinateCache;

	@Autowired
	public WeatherService(OpenWeatherMapClient weatherClient, CityNameCacheDecorator cityNameCache,
			TemperatureCacheService temperatureCache, CityCoordinateDecorator cityCoordinateCache) {
		this.weatherClient = weatherClient;
		this.cityNameCache = cityNameCache;
		this.temperatureCache = temperatureCache;
		this.cityCoordinateCache = cityCoordinateCache;
	}

	public Double searchWeatherByCityName(String cityName) throws OpenWeatherMapResultException {
		Double temperature = null;

		// search id in redis
		CityNameCache cityCached = cityNameCache.find(cityName);
		if (cityCached != null) {
			temperature = searchTemperatureCached(cityCached.getId());
		}

		// not found in redis -> search in api
		if (temperature == null) {
			ResultCityWeather response = null;

			if (cityCached != null) {
				response = weatherClient.getWeatherById(Long.valueOf(cityCached.getId()));
			} else {
				response = weatherClient.getWeatherByCityMap(cityName);
			}

			if (response != null) {
				cacheValues(response);
				temperature = response.getTemperature();
			} else {
				throw new OpenWeatherMapResultException("Cannot retrieve current temperature for your city");
			}
		}
		return temperature;
	}

	public Double searchWeatherByLatLong(Double latitude, Double longitude) throws OpenWeatherMapResultException {
		Double temperature = null;

		// search id in redis
		CityCoordinateCache cityCached = cityCoordinateCache
				.find(String.format(CITY_COORDINATE_KEY_PATTERN, latitude, longitude));
		if (cityCached != null) {
			temperature = searchTemperatureCached(cityCached.getId());
		}

		// not found in redis -> search in api
		if (temperature == null) {
			ResultCityWeather response = null;
			if (cityCached != null) {
				response = weatherClient.getWeatherById(Long.valueOf(cityCached.getId()));
			} else {
				response = weatherClient.getWeatherByLatLong(latitude, longitude);
			}

			if (response != null) {
				cacheValues(response);
				temperature = response.getTemperature();
			} else {
				throw new OpenWeatherMapResultException("Cannot retrieve current temperature for your city");
			}
		}
		return temperature;
	}

	private void cacheValues(ResultCityWeather response) {
		if (response.getId() != null) {
			if (response.getTemperature() != null) {
				temperatureCache
						.save(new TemperatureCache(response.getId().toString(), response.getTemperature().toString()));
			}
			if (response.getName() != null) {
				cityNameCache.save(new CityNameCache(response.getId().toString(), response.getName()));
			}
			if (response.getLatitude() != null && response.getLongitude() != null) {
				cityCoordinateCache.save(new CityCoordinateCache(response.getId().toString(),
						String.format(CITY_COORDINATE_KEY_PATTERN, response.getLatitude(), response.getLongitude())));
			}
		}
	}

	private Double searchTemperatureCached(String string) {
		Double temperature;
		// search temp in redis
		TemperatureCache temperatureCached = temperatureCache.find(string);
		try {
			temperature = temperatureCached != null ? Double.valueOf(temperatureCached.getTemperature()) : null;
		} catch (Exception e) {
			temperature = null;
		}
		return temperature;
	}

}
