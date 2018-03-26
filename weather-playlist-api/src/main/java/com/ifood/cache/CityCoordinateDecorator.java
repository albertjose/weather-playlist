package com.ifood.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.ifood.domain.cache.CityCoordinateCache;

@Service
public class CityCoordinateDecorator extends CacheDecorator<CityCoordinateCache> {

	public CityCoordinateDecorator(CrudRepository<CityCoordinateCache, String> repository) {
		super(repository);
	}

}
