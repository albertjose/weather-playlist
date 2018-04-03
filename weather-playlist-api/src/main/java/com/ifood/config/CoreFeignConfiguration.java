package com.ifood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;

@Configuration
public class CoreFeignConfiguration {

	@Bean
	@Primary
	@Scope("prototype")
	Encoder feignFormEncoder() {
		return new FormEncoder();
	}

	@Bean
	@Primary
	@Scope("prototype")
	Decoder gsonDecoder() {
		return new JacksonDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.BASIC;
	}
}
