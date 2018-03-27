package com.ifood.service;

import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.exception.WeatherPlaylistException;

public interface WeatherPlaylistService {
	WeatherPlaylistResponse getPlayListByWeatherCityName(String cityName) throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException;

	WeatherPlaylistResponse getPlayListByWeatherCoordinates(Double lat, Double lon) throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException;
}
