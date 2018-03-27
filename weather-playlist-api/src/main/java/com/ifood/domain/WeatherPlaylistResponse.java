package com.ifood.domain;

import java.util.List;

public class WeatherPlaylistResponse {
	private Double currentTemperature;
	private List<TrackResponse> tracks;

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

}
