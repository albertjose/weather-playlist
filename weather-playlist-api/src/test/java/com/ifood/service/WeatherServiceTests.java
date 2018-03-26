package com.ifood.service;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifood.cache.CityCoordinateDecorator;
import com.ifood.cache.CityNameCacheDecorator;
import com.ifood.cache.TemperatureCacheService;
import com.ifood.client.OpenWeatherMapClient;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.service.client.WeatherService;

@RunWith(SpringRunner.class)
public class WeatherServiceTests {

	@Mock
	OpenWeatherMapClient weatherClient;

	@Mock
	CityNameCacheDecorator cityNameCache;

	@Mock
	TemperatureCacheService temperatureCache;

	@Mock
	CityCoordinateDecorator cityCoordinateCache;

	WeatherService weatherService;

	public void setUp() throws Exception {
		weatherService = new WeatherService(weatherClient, cityNameCache, temperatureCache, cityCoordinateCache);
	}

	public void searchWeatherByCityName_found() throws OpenWeatherMapResultException {
		weatherService.searchWeatherByCityName("London");
	}

	public void searchWeatherByCityName_notFound() throws OpenWeatherMapResultException {
		weatherService.searchWeatherByCityName("Raccoon City");
	}

	public void searchWeatherByLatLong_found() throws OpenWeatherMapResultException {
		weatherService.searchWeatherCoordinates(55.5, 180.0);
	}

	public void searchWeatherByLatLong_notFound() throws OpenWeatherMapResultException {
		weatherService.searchWeatherCoordinates(55.5, 180.0);
	}

}
