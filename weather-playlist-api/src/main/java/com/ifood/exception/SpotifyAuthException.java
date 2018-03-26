package com.ifood.exception;

public class SpotifyAuthException extends Exception {

	private static final long serialVersionUID = 1L;

	public SpotifyAuthException(String message) {
		super(message);
	}

	public SpotifyAuthException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
