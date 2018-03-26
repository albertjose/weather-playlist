package com.ifood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.cache.CityCoordinateCache;

@Repository
public interface CityCoordinateRepository extends CrudRepository<CityCoordinateCache, String> {
}