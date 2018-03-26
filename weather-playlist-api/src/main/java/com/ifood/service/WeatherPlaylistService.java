package com.ifood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.domain.ResultTrack;
import com.ifood.domain.Track;
import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.domain.enums.GenreEnum;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.exception.WeatherPlaylistException;
import com.ifood.helper.MapperHelper;
import com.ifood.service.client.SpotifyService;
import com.ifood.service.client.WeatherService;

@Service
public class WeatherPlaylistService {

	WeatherService weatherService;

	SpotifyService spotifyService;

	MapperHelper mapperHelper;

	@Autowired
	public WeatherPlaylistService(WeatherService weatherService, SpotifyService spotifyService,
			MapperHelper mapperHelper) {
		this.weatherService = weatherService;
		this.spotifyService = spotifyService;
		this.mapperHelper = mapperHelper;
	}

	public WeatherPlaylistResponse getPlayListByWeatherCityName(String cityName) throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		Double temperature = weatherService.searchWeatherByCityName(cityName);
		return getPlaylistByTemperature(temperature);
	}

	public WeatherPlaylistResponse getPlayListByWeatherCoordinates(Double lat, Double lon) throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		Double temperature = weatherService.searchWeatherCoordinates(lat, lon);
		return getPlaylistByTemperature(temperature);
	}

	private WeatherPlaylistResponse getPlaylistByTemperature(Double temperature)
			throws SpotifyResultException, SpotifyAuthException, WeatherPlaylistException {

		String category = GenreEnum.selectCategory(temperature).getCategotyId();
		if (category == null) {
			throw new WeatherPlaylistException("Sorry. We could not find a category for you right now.");
		}

		List<ResultTrack> resultTrack = spotifyService.getTracks(category);

		// parse ResultTrack to Track
		List<Track> listOFTracks = new ArrayList<>();
		try {
			resultTrack.forEach(rt -> listOFTracks.add(mapperHelper.fromObject(rt.getTrack(), Track.class)));
		} catch (Exception e) {
			throw new WeatherPlaylistException("Sorry. We could not find a playlist for you right now.");
		}

		WeatherPlaylistResponse response = new WeatherPlaylistResponse();
		response.setCurrentTemperature(temperature);
		response.setTracks(listOFTracks);

		return response;
	}
}
