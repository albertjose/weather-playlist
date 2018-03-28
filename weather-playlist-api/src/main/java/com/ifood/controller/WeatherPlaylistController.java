package com.ifood.controller;

import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.exception.WeatherPlaylistBadRequestException;
import com.ifood.exception.WeatherPlaylistException;

public interface WeatherPlaylistController {
	WeatherPlaylistResponse getPlayListWeatherName(String name) throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException;

	WeatherPlaylistResponse getPlayListWeatherCoordinate(Double latidude, Double longitude)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException,
			WeatherPlaylistException, WeatherPlaylistBadRequestException;
}
