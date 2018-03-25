package com.ifood.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("temperatures")
public class TemperatureCache {

	@Id
	private String id;
	private String temperature;
	private String dateSync;

	public TemperatureCache(String id, String temperature) {
		this.id = id;
		this.temperature = temperature;
		this.dateSync = Instant.now().toString();
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

	public String getDateSync() {
		return dateSync;
	}

	public void setDateSync(String dateSync) {
		this.dateSync = dateSync;
	}

}
