package com.ifood.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.CityNameCache;

@Service
public class CityNameCacheDecorator extends CacheDecorator<CityNameCache> {

	protected CityNameCacheDecorator(CrudRepository<CityNameCache, String> repository) {
		super(repository);
	}
	
	@Override
	public void save(CityNameCache cache) {
		cache.setName(cache.getName().toUpperCase());
		super.save(cache);
	}

}
