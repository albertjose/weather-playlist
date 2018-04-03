package com.ifood.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ifood.helper.CoordinateHelper;

@Service
public class OpenWeatherService implements WeatherService {
	private static final Logger logger = LoggerFactory.getLogger(OpenWeatherService.class);

	OpenWeatherMapClient weatherClient;

	CityNameCacheDecorator cityNameCache;

	TemperatureCacheService temperatureCache;

	CityCoordinateDecorator cityCoordinateCache;

	@Autowired
	public OpenWeatherService(OpenWeatherMapClient weatherClient, CityNameCacheDecorator cityNameCache,
			TemperatureCacheService temperatureCache, CityCoordinateDecorator cityCoordinateCache) {
		this.weatherClient = weatherClient;
		this.cityNameCache = cityNameCache;
		this.temperatureCache = temperatureCache;
		this.cityCoordinateCache = cityCoordinateCache;
	}

	@Override
	public Double searchWeatherByCityName(String cityName) throws OpenWeatherMapResultException {
		Double temperature = null;

		// search id in redis
		CityNameCache cityCached = cityNameCache.find(cityName);
		if (cityCached != null) {
			temperature = searchTemperatureCached(cityCached.getCityId());
		}

		// not found in redis -> search in api
		if (temperature == null) {
			ResultCityWeather response = null;

			if (cityCached != null) {
				response = weatherClient.getWeatherById(Long.valueOf(cityCached.getCityId()));
			} else {
				response = weatherClient.getWeatherByCityName(cityName);
			}

			if (response != null) {
				cacheValues(response);
				temperature = response.getTemperature();
			} else {
				throw new OpenWeatherMapResultException("Sorry. Cannot retrieve current temperature for your city.");
			}
		}
		return temperature;
	}

	@Override
	public Double searchWeatherCoordinates(Double latitude, Double longitude) throws OpenWeatherMapResultException {
		Double temperature = null;

		// search id in redis
		CityCoordinateCache cityCached = cityCoordinateCache
				.find(CoordinateHelper.formatCoordinate(latitude, longitude));
		if (cityCached != null) {
			temperature = searchTemperatureCached(cityCached.getCityId());
		}

		// not found in redis -> search in api
		if (temperature == null) {
			ResultCityWeather response = null;
			if (cityCached != null) {
				response = weatherClient.getWeatherById(Long.valueOf(cityCached.getCityId()));
			} else {
				response = weatherClient.getWeatherByLatLong(latitude, longitude);
			}

			if (response != null) {
				cacheValues(response);
				temperature = response.getTemperature();
			} else {
				throw new OpenWeatherMapResultException(
						"Sorry. Cannot retrieve current temperature for your location.");
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
			if (response.getLatitude() != null && response.getLongitude() != null && response.getId() != null) {
				cityCoordinateCache.save(new CityCoordinateCache(response.getId().toString(),
						CoordinateHelper.formatCoordinate(response.getLatitude(), response.getLongitude())));
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
			logger.error(e.getMessage(), e);
			temperature = null;
		}
		return temperature;
	}

}
