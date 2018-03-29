package com.ifood.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifood.client.fallback.OpenWeatherMapClientFallbackFactory;
import com.ifood.config.CoreFeignConfiguration;
import com.ifood.domain.ResultCityWeather;

@FeignClient(url = "${app.open-weather-map.api-url}", name = "open-weather-map-client", fallbackFactory = OpenWeatherMapClientFallbackFactory.class, configuration = CoreFeignConfiguration.class)
public interface OpenWeatherMapClient {

	@GetMapping(value = "?q={city_name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResultCityWeather getWeatherByCityName(@PathVariable("city_name") String city_name);

	@GetMapping(value = "?lat={lat}&lon={lon}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResultCityWeather getWeatherByLatLong(@PathVariable("lat") Double lat, @PathVariable("lon") Double lon);

	@GetMapping(value = "?id={id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResultCityWeather getWeatherById(@PathVariable("id") Long id);
}
