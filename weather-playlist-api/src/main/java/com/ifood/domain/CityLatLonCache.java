package com.ifood.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("cityLatLon")
public class CityLatLonCache {

	private String id;
	@Id
	private String latLon;

	public CityLatLonCache(String id, Double lat, Double lon) {
		super();
		this.id = id;
		this.latLon = String.format("%f:%f", lat, lon);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLatLon() {
		return latLon;
	}

	public void setLatLon(String latLon) {
		this.latLon = latLon;
	}
}
