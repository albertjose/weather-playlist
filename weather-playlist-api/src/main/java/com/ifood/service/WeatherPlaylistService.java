package com.ifood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.domain.Track;
import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.domain.enums.GenreEnum;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.service.client.SpotifyService;
import com.ifood.service.client.WeatherService;

@Service
public class WeatherPlaylistService {

	WeatherService weatherService;

	SpotifyService spotifyService;

	@Autowired
	public WeatherPlaylistService(WeatherService weatherService, SpotifyService spotifyService) {
		this.weatherService = weatherService;
		this.spotifyService = spotifyService;
	}

	public WeatherPlaylistResponse getPlayListByWeatherCityName(String cityName)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException {
		Double temperature = weatherService.searchWeatherByCityName(cityName);
		return getPlaylistByTemperature(temperature);
	}

	public WeatherPlaylistResponse getPlayListByWeatherCityName(Double lat, Double lon)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException {
		Double temperature = weatherService.searchWeatherByLatLong(lat, lon);
		return getPlaylistByTemperature(temperature);
	}

	private WeatherPlaylistResponse getPlaylistByTemperature(Double temperature)
			throws SpotifyResultException, SpotifyAuthException {

		String category = GenreEnum.selectCategory(temperature).getCategotyId();
		if (category == null) {

		}

		List<Track> tracks = spotifyService.getTracks(category);

		WeatherPlaylistResponse response = new WeatherPlaylistResponse();
		response.setCurrentTemperature(temperature);
		response.setTracks(tracks);

		return response;
	}
}
