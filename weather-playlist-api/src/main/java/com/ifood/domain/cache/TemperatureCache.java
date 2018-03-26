package com.ifood.domain.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("temperatures")
public class TemperatureCache {

	@Id
	private String id;
	private String temperature;

	@TimeToLive(unit = TimeUnit.SECONDS)
	private Long expires_in;

	public TemperatureCache(String id, String temperature) {
		this.id = id;
		this.temperature = temperature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

}
