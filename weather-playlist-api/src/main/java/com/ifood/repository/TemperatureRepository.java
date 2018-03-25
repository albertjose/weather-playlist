package com.ifood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.TemperatureCache;

@Repository
public interface TemperatureRepository extends CrudRepository<TemperatureCache, String> {
}
