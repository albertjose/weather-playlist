package com.ifood.domain.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("temperatures")
public class TemperatureCache implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cityId;
	private String temperature;

	@TimeToLive(unit = TimeUnit.SECONDS)
	private Long expiresIn;

	public TemperatureCache(String cityId, String temperature) {
		this.cityId = cityId;
		this.temperature = temperature;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
