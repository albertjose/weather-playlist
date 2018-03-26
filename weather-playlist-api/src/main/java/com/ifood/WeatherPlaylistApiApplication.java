package com.ifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableHystrix
@EnableFeignClients(basePackages = { "com.ifood.client" })
@SpringBootApplication
public class WeatherPlaylistApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherPlaylistApiApplication.class, args);
	}
}
