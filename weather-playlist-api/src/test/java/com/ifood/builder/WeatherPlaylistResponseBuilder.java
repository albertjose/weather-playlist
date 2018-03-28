package com.ifood.builder;

import java.util.Arrays;
import java.util.List;

import com.ifood.domain.TrackResponse;
import com.ifood.domain.WeatherPlaylistResponse;

public class WeatherPlaylistResponseBuilder {

	private static final double CURRENT_TEMPERATURE = 9.0;
	private List<TrackResponse> tracks = Arrays.asList(TrackResponseBuilder.build().now());
	private Double currentTemperature = CURRENT_TEMPERATURE;

	public static WeatherPlaylistResponseBuilder build() {
		return new WeatherPlaylistResponseBuilder();
	}

	public WeatherPlaylistResponse now() {
		return new WeatherPlaylistResponse(currentTemperature, tracks);
	}

}
