package com.ifood.cache;

import java.time.Duration;
import java.time.Instant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.TemperatureCache;

@Service
public class TemperatureCacheService extends CacheDecorator<TemperatureCache> {

	protected TemperatureCacheService(CrudRepository<TemperatureCache, String> repository) {
		super(repository);
	}

	@Override
	public TemperatureCache find(String keyid) {
		TemperatureCache temperatureCache = super.find(keyid);
		if (temperatureCache != null
				&& (10 < Duration.between(Instant.parse(temperatureCache.getDateSync()), Instant.now()).toMinutes())) {
			repository.deleteById(keyid);
			return null;
		}
		return temperatureCache;

	}

}
