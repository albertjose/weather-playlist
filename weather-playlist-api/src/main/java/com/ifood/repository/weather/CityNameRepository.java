package com.ifood.repository.weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.cache.CityNameCache;

@Repository
public interface CityNameRepository extends CrudRepository<CityNameCache, String> {
}
