package com.ifood.domain.cache;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("cityCoordinate")
public class CityCoordinateCache {

	private String id;
	@Id
	private String latLon;

	public CityCoordinateCache(String id, String latLon) {
		super();
		this.id = id;
		this.latLon = latLon;
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
