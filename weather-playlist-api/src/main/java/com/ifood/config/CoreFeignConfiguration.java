package com.ifood.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.FormEncoder;

@Configuration
@EnableFeignClients(basePackages = { "com.ifood.client" })
public class CoreFeignConfiguration {
	@Bean
	@Primary
	@Scope("prototype")
	Encoder feignFormEncoder() {
		return new FormEncoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
