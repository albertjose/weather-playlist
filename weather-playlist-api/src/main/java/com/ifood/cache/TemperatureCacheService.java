package com.ifood.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.TemperatureCache;

@Service
public class TemperatureCacheService extends CacheDecorator<TemperatureCache> {

	@Value("${app.cache.temperature-values}")
	private Long temperatureCacheTimeToExpire;

	protected TemperatureCacheService(CrudRepository<TemperatureCache, String> repository) {
		super(repository);
	}

	@Override
	public void save(TemperatureCache cache) {
		cache.setExpires_in(temperatureCacheTimeToExpire);
		super.save(cache);
	}

}
