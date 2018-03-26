package com.ifood.exception;

public class OpenWeatherMapResultException extends Exception {

	private static final long serialVersionUID = 1L;

	public OpenWeatherMapResultException(String message) {
		super(message);
	}

	public OpenWeatherMapResultException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
