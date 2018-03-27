package com.ifood.service.client;

import com.ifood.exception.OpenWeatherMapResultException;

public interface WeatherService {

	Double searchWeatherByCityName(String cityName) throws OpenWeatherMapResultException;

	Double searchWeatherCoordinates(Double latitude, Double longitude) throws OpenWeatherMapResultException;

}
