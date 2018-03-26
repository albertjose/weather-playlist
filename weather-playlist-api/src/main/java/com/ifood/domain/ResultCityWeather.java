package com.ifood.domain;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultCityWeather implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double latitude;
	private Double longitude;
	private Double temperature;

	public ResultCityWeather() {
		super();
	}

	public ResultCityWeather(Long id, String name, Double latitude, Double longitude, Double temperature) {
		super();
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.temperature = temperature;
	}

	@JsonProperty("coord")
	private void unpackCoord(Map<String, Double> coord) {
		latitude = coord.get("lat");
		longitude = coord.get("lon");
	}

	@JsonProperty("main")
	private void unpackMain(Map<String, Double> main) {
		temperature = main.get("temp");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

}
