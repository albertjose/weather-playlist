package com.ifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = { "com.ifood.client" })
public class WeatherPlaylistApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherPlaylistApiApplication.class, args);
	}
}
