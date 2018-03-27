package com.ifood.domain.cache;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("cityCoordinate")
public class CityCoordinateCache implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cityId;
	@Id
	private String latLon;

	public CityCoordinateCache(String cityId, String latLon) {
		super();
		this.cityId = cityId;
		this.latLon = latLon;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getLatLon() {
		return latLon;
	}

	public void setLatLon(String latLon) {
		this.latLon = latLon;
	}
}
