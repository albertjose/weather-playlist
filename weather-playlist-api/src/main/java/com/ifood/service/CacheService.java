package com.ifood.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.domain.CityLatLonCache;
import com.ifood.domain.CityNameCache;
import com.ifood.domain.CityWeatherResponse;
import com.ifood.domain.TemperatureCache;
import com.ifood.repository.CityLatLonRepository;
import com.ifood.repository.CityNameRepository;
import com.ifood.repository.TemperatureRepository;

@Service
public class CacheService {

	@Autowired
	TemperatureRepository temperatureRepository;

	@Autowired
	CityNameRepository cityNameRepository;

	@Autowired
	CityLatLonRepository cityLatLonRepository;

	public void saveCityTemperatureCache(CityWeatherResponse response) {
		// save infos
		temperatureRepository
				.save(new TemperatureCache(response.getId().toString(), response.getTemperature().toString()));
		cityNameRepository.save(new CityNameCache(response.getId().toString(), response.getName()));
		cityLatLonRepository.save(
				new CityLatLonCache(response.getId().toString(), response.getLatitude(), response.getLongitude()));
	}

	public String findCachedId(String cityName) {
		Optional<CityNameCache> city = cityNameRepository.findById(cityName);
		if (city.isPresent()) {
			return city.get().getId();
		}
		return null;
	}

	public String findCachedId(Double lat, Double lon) {
		if (lat != null && lon != null) {
			Optional<CityNameCache> city = cityNameRepository.findById(String.format("%f:%f", lat, lon));
			if (city.isPresent()) {
				return city.get().getId();
			}
		}
		return null;
	}

	public Double findCachedTemperature(String id) {
		Optional<TemperatureCache> t = temperatureRepository.findById(id);
		if (t.isPresent()) {
			// cache
			if (10 < Duration.between(Instant.parse(t.get().getDateSync()), Instant.now()).toMinutes()) {
				temperatureRepository.deleteById(id);
				return null;
			}
		}
		try {
			return new Double(t.get().getTemperature());
		} catch (Exception e) {
			return null;
		}
	}

}
