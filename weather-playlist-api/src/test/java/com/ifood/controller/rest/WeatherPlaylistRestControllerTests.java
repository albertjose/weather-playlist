package com.ifood.controller.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ifood.builder.WeatherPlaylistResponseBuilder;
import com.ifood.domain.WeatherPlaylistResponse;
import com.ifood.exception.WeatherPlaylistExceptionHandler;
import com.ifood.service.OpenWeatherSpotifyService;
import com.ifood.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WeatherPlaylistRestControllerTests {

	private MockMvc mockMvc;

	@InjectMocks
	private WeatherPlaylistRestController weatherPlaylistRestController;

	@Mock
	OpenWeatherSpotifyService openWeatherSpotifyService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		final StaticApplicationContext applicationContext = new StaticApplicationContext();
		applicationContext.registerSingleton("exceptionHandler", WeatherPlaylistExceptionHandler.class);

		final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
		webMvcConfigurationSupport.setApplicationContext(applicationContext);

		mockMvc = MockMvcBuilders.standaloneSetup(weatherPlaylistRestController)
				.setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver()).build();

	}

	@Test
	public void getNotFoundResource() throws Exception {
		mockMvc.perform(get("/weather-playlist/random/teste")).andExpect(status().isNotFound());
	}

	@Test
	public void getPlayListWeatherName_found() throws Exception {
		WeatherPlaylistResponse trackResult = WeatherPlaylistResponseBuilder.build().now();
		String cityName = "London";
		when(openWeatherSpotifyService.getPlayListByWeatherCityName(cityName)).thenReturn(trackResult);

		mockMvc.perform(get("/weather-playlist/?city_name=" + cityName)).andExpect(status().isOk())
				.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("currentTemperature", is(trackResult.getCurrentTemperature())))
				.andExpect(jsonPath("tracks[0].name", is(trackResult.getTracks().get(0).getName())));
	}

	@Test
	public void getPlayListWeatherCoordinate_found() throws Exception {
		WeatherPlaylistResponse trackResult = WeatherPlaylistResponseBuilder.build().now();
		Double latitude = 14.6, longitude = 14.6;
		when(openWeatherSpotifyService.getPlayListByWeatherCoordinates(latitude, longitude)).thenReturn(trackResult);

		mockMvc.perform(get(String.format("/weather-playlist/?lat=%s&lon=%s", "14.6", "14.6")))
				.andExpect(status().isOk()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("currentTemperature", is(trackResult.getCurrentTemperature())))
				.andExpect(jsonPath("tracks[0].name", is(trackResult.getTracks().get(0).getName())));
	}

	@Test
	public void getPlayListWeatherCoordinate_coordinatesInvalid() throws Exception {
		mockMvc.perform(get(String.format("/weather-playlist/?lat=%s&lon=%s", "140.60", "250.65")))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("message", is("Coordinate points are invalid.")));
	}

	@Test
	public void getPlayListWeatherCoordinate_coordinatesBlankLatLon() throws Exception {
		mockMvc.perform(get(String.format("/weather-playlist/?lat=%s&lon=%s", "", "34.6")))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("message", is("You must provide coordinate points (lat, lon)")));
		mockMvc.perform(get(String.format("/weather-playlist/?lat=%s&lon=%s", "35.6", "")))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("message", is("You must provide coordinate points (lat, lon)")));
	}

}
