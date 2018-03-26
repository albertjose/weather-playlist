package com.ifood.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.service.WeatherPlaylistService;

@RestController
@RequestMapping(value = "/weather-playlist")
public class WeatherPlaylistRest {
	private static final Logger logger = LoggerFactory.getLogger(WeatherPlaylistRest.class);

	@Autowired
	WeatherPlaylistService weatherPlaylistService;

	@GetMapping(value = "/")
	@ResponseStatus(HttpStatus.OK)
	public WeatherPlaylistResponse getPlayListByWeather(@RequestParam(value = "city_name") String cityName)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException {
		logger.debug("Searching playlist by cityName=" + cityName);
		return weatherPlaylistService.getPlayListByWeatherCityName(cityName);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public WeatherPlaylistResponse getPlayListByWeather(@RequestParam(value = "lat") Double latidude,
			@RequestParam(value = "lon") Double longitude)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException {
		logger.debug(String.format("Searching playlist by coordinates: lat=%s lon=%s", latidude, longitude));
		return weatherPlaylistService.getPlayListByWeatherCityName(latidude, longitude);
	}

}
