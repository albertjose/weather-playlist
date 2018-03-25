package com.ifood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.domain.CityLatLonCache;

@Repository
public interface CityLatLonRepository extends CrudRepository<CityLatLonCache, String> {
}