package com.ifood.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.ifood.builder.ResultPlaylistItemBuilder;
import com.ifood.builder.ResultTrackPlaylistBuilder;
import com.ifood.domain.GenreFactory;
import com.ifood.domain.ResultPlaylistItem;
import com.ifood.domain.ResultTrack;
import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.exception.OpenWeatherMapResultException;
import com.ifood.exception.SpotifyAuthException;
import com.ifood.exception.SpotifyResultException;
import com.ifood.exception.WeatherPlaylistException;
import com.ifood.helper.MapperHelper;
import com.ifood.service.client.OpenWeatherService;
import com.ifood.service.client.SpotifyPlaylistService;

@RunWith(SpringRunner.class)
public class OpenWeatherSpotifyServiceTests {

	@Mock
	OpenWeatherService openWeatherService;

	@Mock
	SpotifyPlaylistService spotifyPlaylistService;

	MapperHelper mapperHelper;

	OpenWeatherSpotifyService openWeatheSpotifyService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		mapperHelper = new MapperHelper(new ModelMapper());
		openWeatheSpotifyService = new OpenWeatherSpotifyService(openWeatherService, spotifyPlaylistService,
				mapperHelper);
	}

	@Test
	public void getPlayListByWeatherCityName_foundTracks() throws SpotifyResultException, OpenWeatherMapResultException,
			SpotifyAuthException, WeatherPlaylistException {
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		List<ResultTrack> items = ResultTrackPlaylistBuilder.build().now().getItems();
		Double temperature = 9.0;

		when(openWeatherService.searchWeatherByCityName("London")).thenReturn(temperature);
		when(spotifyPlaylistService
				.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName()))
						.thenReturn(playlist);
		when(spotifyPlaylistService.getTracksByPlaylist(playlist.getId())).thenReturn(items);

		WeatherPlaylistResponse result = openWeatheSpotifyService.getPlayListByWeatherCityName("London");
		Assert.assertEquals(result.getTracks().get(0).getName(), items.get(0).getTrack().getName());
		Assert.assertEquals(result.getCurrentTemperature(), temperature);
	}

	@Test
	public void getPlayListByWeatherCityName_notFoundTemperature() throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		expectedEx.expect(WeatherPlaylistException.class);
		expectedEx.expectMessage("Sorry. We could not find the temperature of your city.");

		when(openWeatherService.searchWeatherByCityName("London")).thenReturn(null);
		openWeatheSpotifyService.getPlayListByWeatherCityName("London");
	}

	@Test
	public void getPlayListByWeatherCityName_notFoundPlaylist() throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		expectedEx.expect(WeatherPlaylistException.class);
		expectedEx.expectMessage("Sorry. We could not find a playlist for you right now.");
		Double temperature = 9.0;

		when(openWeatherService.searchWeatherByCityName("London")).thenReturn(temperature);
		when(spotifyPlaylistService
				.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName()))
						.thenReturn(null);
		openWeatheSpotifyService.getPlayListByWeatherCityName("London");
	}

	@Test
	public void getPlayListByWeatherCityName_notFoundTrackst() throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		expectedEx.expect(WeatherPlaylistException.class);
		expectedEx.expectMessage("Sorry. We could not find a track set for you right now.");
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		Double temperature = 9.0;

		when(openWeatherService.searchWeatherByCityName("London")).thenReturn(temperature);
		when(spotifyPlaylistService
				.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName()))
						.thenReturn(playlist);
		when(spotifyPlaylistService.getTracksByPlaylist(playlist.getId())).thenReturn(null);

		openWeatheSpotifyService.getPlayListByWeatherCityName("London");
	}

	@Test
	public void getPlayListByWeatherCoordinates_foundTracks() throws SpotifyResultException,
			OpenWeatherMapResultException, SpotifyAuthException, WeatherPlaylistException {
		ResultPlaylistItem playlist = ResultPlaylistItemBuilder.build().now();
		List<ResultTrack> items = ResultTrackPlaylistBuilder.build().now().getItems();
		Double temperature = 9.0, latitude = 15.5, longitude = 12.5;

		when(openWeatherService.searchWeatherCoordinates(latitude, longitude)).thenReturn(temperature);
		when(spotifyPlaylistService
				.getRandomPlaylistByCategory(GenreFactory.getGenreByTemperature(temperature).getName()))
						.thenReturn(playlist);
		when(spotifyPlaylistService.getTracksByPlaylist(playlist.getId())).thenReturn(items);

		WeatherPlaylistResponse result = openWeatheSpotifyService.getPlayListByWeatherCoordinates(latitude, longitude);
		Assert.assertEquals(result.getTracks().get(0).getName(), items.get(0).getTrack().getName());
		Assert.assertEquals(result.getCurrentTemperature(), temperature);
	}

}
