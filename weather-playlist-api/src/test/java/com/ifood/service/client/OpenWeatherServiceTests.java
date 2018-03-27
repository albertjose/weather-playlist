package com.ifood.service.client;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

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
import com.ifood.service.client.OpenWeatherService;

@RunWith(SpringRunner.class)
public class OpenWeatherServiceTests {

	@Mock
	OpenWeatherMapClient weatherClient;

	@Mock
	CityNameCacheDecorator cityNameCache;

	@Mock
	TemperatureCacheService temperatureCache;

	@Mock
	CityCoordinateDecorator cityCoordinateCache;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	OpenWeatherService weatherService;

	@Before
	public void setUp() throws Exception {
		weatherService = new OpenWeatherService(weatherClient, cityNameCache, temperatureCache, cityCoordinateCache);
	}

	@Test
	public void searchWeatherByCityName_foundAllInCache_getTemperatureInCache() throws OpenWeatherMapResultException {
		ResultCityWeather city = createWeatherCityReponse();
		when(cityNameCache.find(city.getName())).thenReturn(createCityNameCache(city));
		when(temperatureCache.find(city.getId().toString())).thenReturn(createTemperatureCache(city));

		Double result = weatherService.searchWeatherByCityName(city.getName());
		Assert.assertEquals(result, city.getTemperature());
	}

	@Test
	public void searchWeatherByCityName_foundJustIdInCache_getWeatherByIdInClient()
			throws OpenWeatherMapResultException {
		ResultCityWeather city = createWeatherCityReponse();
		when(cityNameCache.find(city.getName())).thenReturn(createCityNameCache(city));
		when(temperatureCache.find(city.getId().toString())).thenReturn(null);
		when(weatherClient.getWeatherById(city.getId())).thenReturn(createWeatherCityReponse());

		Double result = weatherService.searchWeatherByCityName(city.getName());
		Assert.assertEquals(result, city.getTemperature());
	}

	@Test
	public void searchWeatherByCityName_dontFoundInCache_getWeatherByCityNameClient()
			throws OpenWeatherMapResultException {
		ResultCityWeather city = createWeatherCityReponse();
		when(cityNameCache.find(city.getName())).thenReturn(null);
		when(temperatureCache.find(city.getId().toString())).thenReturn(null);
		when(weatherClient.getWeatherByCityName(city.getName())).thenReturn(createWeatherCityReponse());

		Double result = weatherService.searchWeatherByCityName(city.getName());
		Assert.assertEquals(result, city.getTemperature());
	}

	@Test
	public void searchWeatherByCityName_dontFoundInCache_dontFindInClient() throws OpenWeatherMapResultException {
		expectedEx.expect(OpenWeatherMapResultException.class);
		expectedEx.expectMessage("Sorry. Cannot retrieve current temperature for your city.");
		ResultCityWeather city = createWeatherCityReponse();
		when(cityNameCache.find(city.getName())).thenReturn(null);
		when(temperatureCache.find(city.getId().toString())).thenReturn(null);
		when(weatherClient.getWeatherByCityName(city.getName())).thenReturn(null);

		weatherService.searchWeatherByCityName(city.getName());
	}

	@Test
	public void searchWeatherCoordinates_foundAllInCache_getTemperatureInCache() throws OpenWeatherMapResultException {
		ResultCityWeather city = createWeatherCityReponse();
		String coordinateKeyCache = CoordinateHelper.formatCoordinate(city.getLatitude(), city.getLongitude());
		when(cityCoordinateCache.find(coordinateKeyCache))
				.thenReturn(createCityCoordinateCache(city, coordinateKeyCache));
		when(temperatureCache.find(city.getId().toString())).thenReturn(createTemperatureCache(city));

		Double result = weatherService.searchWeatherCoordinates(city.getLatitude(), city.getLongitude());
		Assert.assertEquals(result, city.getTemperature());
	}

	@Test
	public void searchWeatherCoordinates_foundJustIdInCache_getWeatherByIdInClient()
			throws OpenWeatherMapResultException {
		ResultCityWeather city = createWeatherCityReponse();
		String coordinateKeyCache = CoordinateHelper.formatCoordinate(city.getLatitude(), city.getLongitude());
		when(cityCoordinateCache.find(coordinateKeyCache))
				.thenReturn(createCityCoordinateCache(city, coordinateKeyCache));
		when(temperatureCache.find(city.getId().toString())).thenReturn(null);
		when(weatherClient.getWeatherById(city.getId())).thenReturn(city);

		Double result = weatherService.searchWeatherCoordinates(city.getLatitude(), city.getLongitude());
		Assert.assertEquals(result, city.getTemperature());
	}

	@Test
	public void searchWeatherCoordinates_dontFoundInCache_getWeatherByLatLongClient()
			throws OpenWeatherMapResultException {
		ResultCityWeather city = createWeatherCityReponse();
		String coordinateKeyCache = CoordinateHelper.formatCoordinate(city.getLatitude(), city.getLongitude());
		when(cityCoordinateCache.find(coordinateKeyCache)).thenReturn(null);
		when(temperatureCache.find(city.getId().toString())).thenReturn(null);
		when(weatherClient.getWeatherByLatLong(city.getLatitude(), city.getLongitude())).thenReturn(city);

		Double result = weatherService.searchWeatherCoordinates(city.getLatitude(), city.getLongitude());
		Assert.assertEquals(result, city.getTemperature());
	}

	@Test
	public void searchWeatherCoordinates_dontFoundInCache_dontFindInClient() throws OpenWeatherMapResultException {
		expectedEx.expect(OpenWeatherMapResultException.class);
		expectedEx.expectMessage("Sorry. Cannot retrieve current temperature for your location.");
		ResultCityWeather city = createWeatherCityReponse();
		String coordinateKeyCache = CoordinateHelper.formatCoordinate(city.getLatitude(), city.getLongitude());
		when(cityCoordinateCache.find(coordinateKeyCache)).thenReturn(null);
		when(temperatureCache.find(city.getId().toString())).thenReturn(null);
		when(weatherClient.getWeatherByLatLong(city.getLatitude(), city.getLongitude())).thenReturn(null);

		weatherService.searchWeatherCoordinates(city.getLatitude(), city.getLongitude());
		weatherService.searchWeatherByCityName(city.getName());
	}

	private ResultCityWeather createWeatherCityReponse() {
		return new ResultCityWeather(1L, "London", 1.0, 1.0, 30.0);
	}

	private CityNameCache createCityNameCache(ResultCityWeather city) {
		return new CityNameCache(city.getId().toString(), city.getName());
	}

	private TemperatureCache createTemperatureCache(ResultCityWeather city) {
		return new TemperatureCache(city.getId().toString(), city.getTemperature().toString());
	}

	private CityCoordinateCache createCityCoordinateCache(ResultCityWeather city, String coordinateKeyCache) {
		return new CityCoordinateCache(city.getId().toString(), coordinateKeyCache);
	}

}
