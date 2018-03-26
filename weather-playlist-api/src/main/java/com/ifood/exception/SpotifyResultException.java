package com.ifood.exception;

public class SpotifyResultException extends Exception {

	private static final long serialVersionUID = 1L;

	public SpotifyResultException(String message) {
		super(message);
	}

	public SpotifyResultException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
