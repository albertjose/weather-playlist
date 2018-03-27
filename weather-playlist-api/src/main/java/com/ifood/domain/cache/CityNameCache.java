package com.ifood.domain.cache;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("cityName")
public class CityNameCache implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cityId;
	@Id
	private String name;

	public CityNameCache(String cityId, String name) {
		this.cityId = cityId;
		this.name = name;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
