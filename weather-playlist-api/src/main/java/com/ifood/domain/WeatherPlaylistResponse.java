package com.ifood.domain;

import java.util.List;

public class WeatherPlaylistResponse {
	private Double currentTemperature;
	private String suggestedGenre;
	private List<TrackResponse> tracks;

	public WeatherPlaylistResponse() {
		super();
	}

	public WeatherPlaylistResponse(Double currentTemperature, String suggestedGenre, List<TrackResponse> tracks) {
		super();
		this.currentTemperature = currentTemperature;
		this.suggestedGenre = suggestedGenre;
		this.tracks = tracks;
	}

	public Double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(Double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public List<TrackResponse> getTracks() {
		return tracks;
	}

	public void setTracks(List<TrackResponse> tracks) {
		this.tracks = tracks;
	}

	public String getSuggestedGenre() {
		return suggestedGenre;
	}

	public void setSuggestedGenre(String suggestedGenre) {
		this.suggestedGenre = suggestedGenre;
	}

}
