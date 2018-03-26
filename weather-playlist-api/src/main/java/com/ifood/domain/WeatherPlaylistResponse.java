package com.ifood.domain;

import java.util.List;

public class WeatherPlaylistResponse {
	private Double currentTemperature;
	private List<Track> tracks;

	public Double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(Double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

}
