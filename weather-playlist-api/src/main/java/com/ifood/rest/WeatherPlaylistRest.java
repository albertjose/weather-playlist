package com.ifood.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.service.WeatherPlaylistService;

@RestController
@RequestMapping(value = "/weather-playlist")
public class WeatherPlaylistRest {

	@Autowired
	WeatherPlaylistService weatherPlaylistService;

	@GetMapping(value = "/")
	public void getPlayListByWeather(@RequestParam(value = "city_name") String cityName) {
		weatherPlaylistService.getPlayListByWeatherCityName(cityName);
	}

	@GetMapping
	public void getPlayListByWeather(@RequestParam(value = "lat") String lat, @RequestParam(value = "lon") String lon) {

	}

}
