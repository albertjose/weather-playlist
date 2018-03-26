package com.ifood.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ifood.client.fallback.OpenWeatherMapClientFallback;
import com.ifood.domain.ResultCityWeather;

@FeignClient(url = "${app.open-weather-map.api-url}", name = "open-weather-map-client", fallback = OpenWeatherMapClientFallback.class)
public interface OpenWeatherMapClient {

	@GetMapping(value = "?q={city_name}")
	ResultCityWeather getWeatherByCityMap(@PathVariable("city_name") String city_name);

	@GetMapping(value = "?lat={lat}&lon={lon}")
	ResultCityWeather getWeatherByLatLong(@PathVariable("lat") Double lat, @PathVariable("lat") Double lon);

	@GetMapping(value = "?id={id}")
	ResultCityWeather getWeatherById(@PathVariable("id") Long id);
}
