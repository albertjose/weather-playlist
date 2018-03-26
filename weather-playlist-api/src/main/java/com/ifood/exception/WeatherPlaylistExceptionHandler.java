package com.ifood.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ifood.domain.ErrorResponse;

@ControllerAdvice
public class WeatherPlaylistExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(WeatherPlaylistExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> defaultErrorHandler(Exception e) {
		logger.error("Exception handled", e);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage()));
	}

	@ExceptionHandler(value = SpotifyAuthException.class)
	public ResponseEntity<ErrorResponse> handleSpotifyAuthException(SpotifyAuthException e) {
		logger.error("SpotifyAuthException handled", e);
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),
				HttpStatus.UNAUTHORIZED.name());
		return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
	}

	@ExceptionHandler(value = { SpotifyResultException.class, OpenWeatherMapResultException.class })
	public ResponseEntity<ErrorResponse> handleClientsResultException(Exception e) {
		logger.error("ClientResultException handled", e);
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				HttpStatus.NOT_FOUND.name());
		return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
	}

	@ExceptionHandler(value = WeatherPlaylistException.class)
	public ResponseEntity<ErrorResponse> handleSpotifyAuthException(WeatherPlaylistException e) {
		logger.error("WeatherPlaylistException handled", e);
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.name());
		return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
	}

}