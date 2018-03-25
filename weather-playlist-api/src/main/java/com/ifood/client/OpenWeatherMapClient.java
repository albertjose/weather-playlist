package com.ifood.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ifood.domain.CityWeatherResponse;

@FeignClient(url = "${app.open-weather-map.url}", name = "open-weather-map-client")
public interface OpenWeatherMapClient {

	@GetMapping(value = "?q={city_name}")
	CityWeatherResponse getWeatherByCityMap(@PathVariable("city_name") String city_name);

	@GetMapping(value = "?lat={lat}&lon={lon}")
	CityWeatherResponse getWeatherByLatLong(@PathVariable("lat") Double lat, @PathVariable("lat") Double lon);

	@GetMapping(value = "?id={id}")
	CityWeatherResponse getWeatherById(@PathVariable("id") Long id);
}
