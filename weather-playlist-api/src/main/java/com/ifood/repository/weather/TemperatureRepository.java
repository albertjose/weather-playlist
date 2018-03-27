package com.ifood.repository.weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.cache.TemperatureCache;

@Repository
public interface TemperatureRepository extends CrudRepository<TemperatureCache, String> {
}
