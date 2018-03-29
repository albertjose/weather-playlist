package com.ifood.controller.rest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.controller.WeatherPlaylistController;
import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.exception.WeatherPlaylistBadRequestException;
import com.ifood.exception.WeatherPlaylistException;
import com.ifood.helper.CoordinateHelper;
import com.ifood.service.OpenWeatherSpotifyService;

@RestController
@RequestMapping(value = "/weather-playlist")
public class WeatherPlaylistRestController implements WeatherPlaylistController {
	private static final Logger logger = LoggerFactory.getLogger(WeatherPlaylistRestController.class);

	@Autowired
	OpenWeatherSpotifyService openWeatherSpotifyService;

	@GetMapping(value = "/", params = { "city_name" }, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public WeatherPlaylistResponse getPlayListWeatherName(@RequestParam(value = "city_name") String cityName)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException,
			WeatherPlaylistException, WeatherPlaylistBadRequestException {
		logger.debug("Searching playlist by cityName=" + cityName);

		if (StringUtils.isBlank(cityName)) {
			throw new WeatherPlaylistBadRequestException("You must provide the city name.");
		}
		return openWeatherSpotifyService.getPlayListByWeatherCityName(cityName);
	}

	@GetMapping(value = "/", params = { "lat", "lon" }, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public WeatherPlaylistResponse getPlayListWeatherCoordinate(
			@RequestParam(value = "lat", required = true) Double latidude,
			@RequestParam(value = "lon", required = true) Double longitude)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException,
			WeatherPlaylistException, WeatherPlaylistBadRequestException {
		logger.debug(String.format("Searching playlist by coordinates: lat=%s lon=%s", latidude, longitude));

		if (latidude == null || longitude == null) {
			throw new WeatherPlaylistBadRequestException("You must provide coordinate points (lat, lon)");
		}

		if (!CoordinateHelper.inRange(latidude, longitude)) {
			throw new WeatherPlaylistBadRequestException("Coordinate points are invalid.");
		}

		return openWeatherSpotifyService.getPlayListByWeatherCoordinates(latidude, longitude);
	}

}
