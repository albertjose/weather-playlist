package com.ifood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifood.domain.PlayList;

@Service
public class WeatherPlaylistService {

	@Autowired
	WeatherService weatherService;

	public PlayList getPlayListByWeatherCityName(String cityName) {
		Double temperature = weatherService.searchByCityName(cityName);
		System.out.println(temperature);
		return null;
	}
}
