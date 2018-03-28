package com.ifood.exception;

public class WeatherPlaylistBadRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public WeatherPlaylistBadRequestException(String message) {
		super(message);
	}

	public WeatherPlaylistBadRequestException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
