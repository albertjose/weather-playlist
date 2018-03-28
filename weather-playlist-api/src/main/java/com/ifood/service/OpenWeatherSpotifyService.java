package com.ifood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.domain.ResultPlaylistItem;
import com.ifood.domain.ResultTrack;
import com.ifood.domain.TrackResponse;
import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.domain.enums.GenreEnum;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.exception.WeatherPlaylistException;
import com.ifood.helper.MapperHelper;
import com.ifood.service.client.OpenWeatherService;
import com.ifood.service.client.SpotifyPlaylistService;

@Service
public class OpenWeatherSpotifyService implements WeatherPlaylistService {

	OpenWeatherService openWeatherService;

	SpotifyPlaylistService spotifyPlaylistService;

	MapperHelper mapperHelper;

	@Autowired
	public OpenWeatherSpotifyService(OpenWeatherService openWeatherService,
			SpotifyPlaylistService spotifyPlaylistService, MapperHelper mapperHelper) {
		this.openWeatherService = openWeatherService;
		this.spotifyPlaylistService = spotifyPlaylistService;
		this.mapperHelper = mapperHelper;
	}

	@Override
	public WeatherPlaylistResponse getPlayListByWeatherCityName(String cityName) throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		Double temperature = openWeatherService.searchWeatherByCityName(cityName);
		return getPlaylistByTemperature(temperature);
	}

	@Override
	public WeatherPlaylistResponse getPlayListByWeatherCoordinates(Double lat, Double lon)
			throws SpotifyResultException, OpenWeatherMapResultException, SpotifyAuthException,
			WeatherPlaylistException {
		Double temperature = openWeatherService.searchWeatherCoordinates(lat, lon);
		return getPlaylistByTemperature(temperature);
	}

	private WeatherPlaylistResponse getPlaylistByTemperature(Double temperature)
			throws SpotifyResultException, SpotifyAuthException, WeatherPlaylistException {
		if (temperature == null) {
			throw new WeatherPlaylistException("Sorry. We could not find the temperature of your city.");
		}

		String category = GenreEnum.selectCategory(temperature).getGenreName();
		if (category == null) {
			throw new WeatherPlaylistException("Sorry. We could not find a category for you right now.");
		}

		// find playlist
		ResultPlaylistItem resultPlaylist = spotifyPlaylistService.getRandomPlaylistByCategory(category);
		if (resultPlaylist == null) {
			throw new WeatherPlaylistException("Sorry. We could not find a playlist for you right now.");
		}

		List<ResultTrack> resultTrack = spotifyPlaylistService.getTracksByPlaylist(resultPlaylist.getId());

		// parse ResultTrack to Track
		List<TrackResponse> listOFTracks = new ArrayList<>();
		try {
			resultTrack.forEach(rt -> listOFTracks.add(mapperHelper.fromObject(rt.getTrack(), TrackResponse.class)));
		} catch (Exception e) {
			throw new WeatherPlaylistException("Sorry. We could not find a track set for you right now.");
		}

		WeatherPlaylistResponse response = new WeatherPlaylistResponse();
		response.setCurrentTemperature(temperature);
		response.setTracks(listOFTracks);

		return response;
	}
}
