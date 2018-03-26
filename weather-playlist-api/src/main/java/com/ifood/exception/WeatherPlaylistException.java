package com.ifood.exception;

public class WeatherPlaylistException extends Exception {

	private static final long serialVersionUID = 1L;

	public WeatherPlaylistException(String message) {
		super(message);
	}

	public WeatherPlaylistException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
