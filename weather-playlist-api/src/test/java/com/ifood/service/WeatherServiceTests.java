package com.ifood.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class WeatherServiceTests {

	@Autowired
	WeatherService weatherPlaylistService;

	public void searchWeatherByCityName_found() {
		weatherPlaylistService.searchByCityName("London");
	}

	public void searchWeatherByCityName_notFound() {
		weatherPlaylistService.searchByCityName("Raccoon City");
	}

	public void searchWeatherByLatLong_found() {
		weatherPlaylistService.searchWeatherByLatLong(55.5, 180.0);
	}

	public void searchWeatherByLatLong_notFound() {
		weatherPlaylistService.searchWeatherByLatLong(55.5, 180.0);
	}

}
